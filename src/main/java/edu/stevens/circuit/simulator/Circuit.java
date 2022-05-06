package edu.stevens.circuit.simulator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Circuit implements Logic {
    private List<Logic> components;
    private List<Contact> inputs;
    private List<Contact> outputs;
    private List<Wire> innerWires;
    private List<String> importables;
    private String name;

    public Circuit(String circuitName, List<Logic> components, List<Contact> inputs,
            List<Contact> outputs, List<Wire> innerWires, List<String> importables) {
        this.name = circuitName;
        this.components = components;
        this.inputs = inputs;
        this.outputs = outputs;
        this.innerWires = innerWires;
        this.importables = importables;
    }

    public Circuit(String circuitName) throws IOException, InvalidLogicParametersException {
        this.components = new ArrayList<>();
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.innerWires = new ArrayList<>();
        this.importables = new ArrayList<>();
        this.name = circuitName;

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

    public Scanner getCircuitScanner(String circuitName) throws IOException {
        String path = "saved_circuits/" + circuitName + ".txt";
        return new Scanner(Path.of(path));
    }

    public void parseImportLine(String line) {
        String[] split = line.split("\\s+");
        for (int i = 1; i < split.length; i += 1) {
            importables.add(split[i]);
        }
    }

    public void parseContactsLine(String line) {
        String[] split = line.split("\\s+");
        boolean isInput = true;
        for (String s : split) {
            if (s.equals("->")) {
                isInput = false;
                continue;
            }

            Wire wInner = new Wire(s);
            Wire wOuter = new Wire(s);
            innerWires.add(wInner);

            Contact c;
            if (isInput) {
                c = new Contact(wOuter, wInner, true);
                inputs.add(c);
                continue;
            }

            c = new Contact(wInner, wOuter, false);
            outputs.add(c);
        }
    }

    public Optional<Wire> findWire(String name) {
        for (Wire w : innerWires) {
            if (w.getName().equals(name)) {
                return Optional.ofNullable(w);
            }
        }
        return Optional.empty();
    }

    public void hookUp(List<Wire> inWires, List<Wire> outWires) throws InvalidLogicParametersException {
        if (inWires.size() != inputs.size()) {
            throw new InvalidLogicParametersException(true, inputs.size(), inWires.size());
        }
        if (outWires.size() != outputs.size()) {
            throw new InvalidLogicParametersException(false, inputs.size(), outWires.size());
        }

        Iterator<Contact> inputsIter = inputs.iterator();
        Iterator<Wire> inWiresIter = inWires.iterator();

        while (inputsIter.hasNext() && inWiresIter.hasNext()) {
            Contact c = inputsIter.next();
            Wire w = inWiresIter.next();
            c.setIn(w);
        }

        Iterator<Contact> outputsIter = outputs.iterator();
        Iterator<Wire> outWiresIter = outWires.iterator();

        while (outputsIter.hasNext() && outWiresIter.hasNext()) {
            Contact c = outputsIter.next();
            Wire w = outWiresIter.next();
            c.setOut(w);
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
        Wire input = findWire(inputWireName).get();
        Wire output;

        Optional<Wire> outputOptional = findWire(outputWireName);
        if (outputOptional.isEmpty()) {
            output = new Wire(outputWireName);
            innerWires.add(output);
        } else {
            output = outputOptional.get();
        }

        components.add(new GateNot(input, output));
    }

    private void parseGate(List<String> split, int gateIndex) throws InvalidLogicParametersException {
        int arrowIndex = split.indexOf("->");
        if (arrowIndex != split.size() - 2) {
            throw new InvalidLogicParametersException(false, 1, arrowIndex);
        }

        List<Wire> ins = new ArrayList<>();
        for (int i = 1; i < arrowIndex; i += 1) {
            String inputName = split.get(i);
            Wire w = findWire(inputName).get();
            ins.add(w);
        }

        String outputName = split.get(split.size() - 1);
        Optional<Wire> outputOptional = findWire(outputName);
        Wire output;
        if (outputOptional.isEmpty()) {
            output = new Wire(outputName);
            innerWires.add(output);
        } else {
            output = outputOptional.get();
        }

        switch (gateIndex) {
            case 0:
                components.add(new GateAnd(ins, output));
                break;
            case 1:
                components.add(new GateOr(ins, output));
                break;
            case 2:
                components.add(new GateXor(ins, output));
                break;
            case 3:
                components.add(new GateNand(ins, output));
                break;
            case 4:
                components.add(new GateNor(ins, output));
                break;
        }
    }

    public void parseComponentLine(String line) throws IOException, InvalidLogicParametersException {
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
                innerWires.add(output);
            } else {
                outWires.add(w.get());
            }
        }
        c.hookUp(inWires, outWires);
        components.add(c);
    }

    @Override
    public void feed(List<Signal> inSignals) throws InvalidLogicParametersException {
        if (inSignals.size() != inputs.size()) {
            throw new InvalidLogicParametersException(true, inputs.size(), inSignals.size());
        }

        Iterator<Signal> signalsIter = inSignals.iterator();
        Iterator<Contact> inputsIterator = inputs.iterator();

        while (signalsIter.hasNext() && inputsIterator.hasNext()) {
            Signal s = signalsIter.next();
            Contact c = inputsIterator.next();

            c.getIn().setSignal(s);
        }
    }

    @Override
    public void feedFromString(String inSignals) throws InvalidLogicParametersException, MalformedSignal {
        List<Signal> signals = Signal.fromString(inSignals);
        feed(signals);
    }

    @Override
    public boolean propagate() {
        boolean changed = false;
        for (Contact c : inputs) {
            boolean result = c.propagate();
            changed |= result;
        }
        for (Logic l : components) {
            boolean result = l.propagate();
            changed |= result;
        }
        for (Contact c : outputs) {
            boolean result = c.propagate();
            changed |= result;
        }
        return changed;
    }

    @Override
    public List<Signal> read() {
        return outputs.stream().map(c -> c.getOut().getSignal()).toList();
    }

    @Override
    public List<Signal> inspect(List<Signal> inputs) throws InvalidLogicParametersException {
        feed(inputs);
        propagate();
        return read();
    }

    @Override
    public String inspectFromString(String inputs) throws InvalidLogicParametersException, MalformedSignal {
        List<Signal> i = Signal.fromString(inputs);
        return Signal.toString(inspect(i));
    }

    public static String indent(String s) {
        return s.lines().map(line -> "  " + line + "\n").collect(Collectors.joining());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.name);
        s.append(" : ");
        s.append(inputs.stream().map(i -> i.getIn().toString()).collect(Collectors.joining()));
        s.append(" -> ");
        s.append(outputs.stream().map(o -> o.getOut().toString()).collect(Collectors.joining()));
        s.append("\n");
        for (Logic component : components) {
            s.append(indent(component.toString()));
        }
        return s.toString();
    }

    public List<Logic> getComponents() {
        return components;
    }

    public void setComponents(List<Logic> components) {
        this.components = components;
    }

    public List<Contact> getInputs() {
        return inputs;
    }

    public void setInputs(List<Contact> inputs) {
        this.inputs = inputs;
    }

    public List<Contact> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Contact> outputs) {
        this.outputs = outputs;
    }

    public List<Wire> getInnerWires() {
        return innerWires;
    }

    public void setInnerWires(List<Wire> innerWires) {
        this.innerWires = innerWires;
    }

    public List<String> getImportables() {
        return importables;
    }

    public void setImportables(List<String> importables) {
        this.importables = importables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
