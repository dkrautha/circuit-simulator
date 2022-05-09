package edu.stevens.circuit.simulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FeedbackCircuit extends Circuit {
    public FeedbackCircuit(final String circuitName)
            throws IOException, InvalidLogicParametersException {
        super(circuitName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());

        final Scanner scanner = getCircuitScanner(circuitName);
        boolean contactParsedYet = false;
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            if (!line.isBlank()) {
                if (!contactParsedYet) {
                    if (line.contains("IMPORT")) {
                        parseImportLine(line);
                    } else {
                        parseContactsLine(line);
                        contactParsedYet = true;
                    }
                } else {
                    parseComponentLine(line);
                }
            }
        }
    }

    private void parseNot(final List<String> split) throws InvalidLogicParametersException {
        if (split.size() != 4) {
            final int arrowIndex = split.indexOf("->");
            if (arrowIndex != 2) {
                throw new InvalidLogicParametersException(true, 1, arrowIndex - 1);
            }
            throw new InvalidLogicParametersException(false, 1, split.size() - arrowIndex - 1);
        }

        final String inputWireName = split.get(1);
        final String outputWireName = split.get(3);

        Wire input;
        final Optional<Wire> inputOptional = findWire(inputWireName);
        if (inputOptional.isEmpty()) {
            input = new Wire(inputWireName);
            getInnerWires().add(input);
        } else {
            input = inputOptional.get();
        }

        Wire output;
        final Optional<Wire> outputOptional = findWire(outputWireName);
        if (outputOptional.isEmpty()) {
            output = new Wire(outputWireName);
            getInnerWires().add(output);
        } else {
            output = outputOptional.get();
        }

        getComponents().add(new GateNot(input, output));
    }

    private void parseGate(final List<String> split, final int gateIndex)
            throws InvalidLogicParametersException {
        final int arrowIndex = split.indexOf("->");
        if (arrowIndex != split.size() - 2) {
            throw new InvalidLogicParametersException(false, 1, arrowIndex);
        }

        final List<Wire> inputs = new ArrayList<>();
        for (int i = 1; i < arrowIndex; i += 1) {
            final String inputName = split.get(i);
            Wire input;
            final Optional<Wire> inputOptional = findWire(inputName);
            if (inputOptional.isEmpty()) {
                input = new Wire(inputName);
                getInnerWires().add(input);
            } else {
                input = inputOptional.get();
            }
            inputs.add(input);
        }

        final String outputName = split.get(split.size() - 1);
        final Optional<Wire> outputOptional = findWire(outputName);
        Wire output;
        if (outputOptional.isEmpty()) {
            output = new Wire(outputName);
            getInnerWires().add(output);
        } else {
            output = outputOptional.get();
        }

        final List<Logic> components = getComponents();
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
            default:
        }
    }

    @Override
    public void parseComponentLine(final String line)
            throws InvalidLogicParametersException, IOException {
        final List<String> split = Arrays.asList(line.split("\\s+"));
        final String componentType = split.get(0);
        // unique case for NOT
        if (componentType.equals("NOT")) {
            parseNot(split);
            return;
        }

        // case for other gates
        final List<String> otherGates = Arrays.asList("AND", "OR", "XOR", "NAND", "NOR");
        final int gateIndex = otherGates.indexOf(componentType);
        if (gateIndex >= 0) {
            parseGate(split, gateIndex);
            return;
        }

        // case for a sub-circuit
        Circuit c;
        try {
            c = new Circuit(componentType);
        } catch (final FeedbackCircuitDetectedException e) {
            c = new FeedbackCircuit(componentType);
        }
        final List<Wire> inWires = new ArrayList<>();
        final List<Wire> outWires = new ArrayList<>();
        final int arrowIndex = split.indexOf("->");
        for (int i = 1; i < arrowIndex; i += 1) {
            final String inputName = split.get(i);
            final Wire w = findWire(inputName).get();
            inWires.add(w);
        }
        for (int i = arrowIndex + 1; i < split.size(); i += 1) {
            final String outputName = split.get(i);
            final Optional<Wire> w = findWire(outputName);
            if (w.isEmpty()) {
                final Wire output = new Wire(outputName);
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
        for (final Contact c : getInputs()) {
            final boolean result = c.propagate();
            changed |= result;
        }
        for (final Logic l : getComponents()) {
            final boolean result = l.propagate();
            changed |= result;
        }
        for (final Contact c : getOutputs()) {
            final boolean result = c.propagate();
            changed |= result;
        }

        if (changed) {
            propagate();
        }
        return changed;
    }
}
