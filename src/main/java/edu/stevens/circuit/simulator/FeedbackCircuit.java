package edu.stevens.circuit.simulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FeedbackCircuit extends Circuit {
    public FeedbackCircuit(String circuitName) throws IOException, InvalidLogicParametersException {
        super(circuitName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());

        Scanner scanner = getCircuitScanner(circuitName);
        boolean contactParsedYet = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                continue;
            }
            if (!contactParsedYet) {
                if (line.contains("IMPORT")) {
                    parseImportLine(line);
                    continue;
                }

                parseContactsLine(line);
                contactParsedYet = true;
                continue;
            }

            parseComponentLine(line);
        }
    }

    private void parseNot(List<String> split) throws InvalidLogicParametersException {
        if (split.size() != 4) {
            int arrowIndex = split.indexOf("->");
            if (arrowIndex != 2) {
                throw new InvalidLogicParametersException(true, 1, arrowIndex - 1);
            }
            throw new InvalidLogicParametersException(false, 1, split.size() - arrowIndex - 1);
        }

        String inputWireName = split.get(1);
        String outputWireName = split.get(3);

        Wire input;
        Optional<Wire> inputOptional = findWire(inputWireName);
        if (inputOptional.isEmpty()) {
            input = new Wire(inputWireName);
            getInnerWires().add(input);
        } else {
            input = inputOptional.get();
        }

        Wire output;
        Optional<Wire> outputOptional = findWire(outputWireName);
        if (outputOptional.isEmpty()) {
            output = new Wire(outputWireName);
            getInnerWires().add(output);
        } else {
            output = outputOptional.get();
        }

        getComponents().add(new GateNot(input, output));
    }

    private void parseGate(List<String> split, int gateIndex) throws InvalidLogicParametersException {
        int arrowIndex = split.indexOf("->");
        if (arrowIndex != split.size() - 2) {
            throw new InvalidLogicParametersException(false, 1, arrowIndex);
        }

        List<Wire> inputs = new ArrayList<>();
        for (int i = 1; i < arrowIndex; i += 1) {
            String inputName = split.get(i);
            Wire input;
            Optional<Wire> inputOptional = findWire(inputName);
            if (inputOptional.isEmpty()) {
                input = new Wire(inputName);
                getInnerWires().add(input);
            } else {
                input = inputOptional.get();
            }
            inputs.add(input);
        }

        String outputName = split.get(split.size() - 1);
        Optional<Wire> outputOptional = findWire(outputName);
        Wire output;
        if (outputOptional.isEmpty()) {
            output = new Wire(outputName);
            getInnerWires().add(output);
        } else {
            output = outputOptional.get();
        }

        List<Logic> components = getComponents();
        switch (gateIndex) {
            case 0:
                components.add(new GateAnd(inputs, output));
                break;
            case 1:
                components.add(new GateOr(inputs, output));
                break;
            case 2:
                components.add(new GateXor(inputs, output));
                break;
            case 3:
                components.add(new GateNand(inputs, output));
                break;
            case 4:
                components.add(new GateNor(inputs, output));
                break;
        }
    }

    @Override
    public void parseComponentLine(String line) throws InvalidLogicParametersException, IOException {
        List<String> split = Arrays.asList(line.split("\\s+"));
        String componentType = split.get(0);
        // unique case for NOT
        if (componentType.equals("NOT")) {
            parseNot(split);
            return;
        }

        // case for other gates
        List<String> otherGates = Arrays.asList("AND", "OR", "XOR", "NAND", "NOR");
        int gateIndex = otherGates.indexOf(componentType);
        if (gateIndex >= 0) {
            parseGate(split, gateIndex);
            return;
        }

        // case for a sub-circuit
        Circuit c = new Circuit(componentType);
        List<Wire> inWires = new ArrayList<>();
        List<Wire> outWires = new ArrayList<>();
        int arrowIndex = split.indexOf("->");
        for (int i = 1; i < arrowIndex; i += 1) {
            String inputName = split.get(i);
            Wire w = findWire(inputName).get();
            inWires.add(w);
        }
        for (int i = arrowIndex + 1; i < split.size(); i += 1) {
            String outputName = split.get(i);
            Optional<Wire> w = findWire(outputName);
            if (w.isEmpty()) {
                Wire output = new Wire(outputName);
                outWires.add(output);
                getInnerWires().add(output);
            } else {
                outWires.add(w.get());
            }
        }
        c.hookUp(inWires, outWires);
        getComponents().add(c);
    }

    @Override
    public boolean propagate() {
        boolean changed = false;
        for (Contact c : getInputs()) {
            boolean result = c.propagate();
            changed |= result;
        }
        for (Logic l : getComponents()) {
            boolean result = l.propagate();
            changed |= result;
        }
        for (Contact c : getOutputs()) {
            boolean result = c.propagate();
            changed |= result;
        }

        if (changed) {
            propagate();
        }
        return changed;
    }
}
