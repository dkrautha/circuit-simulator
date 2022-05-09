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

    public Circuit(final String circuitName, final List<Logic> components,
            final List<Contact> inputs, final List<Contact> outputs, final List<Wire> innerWires,
            final List<String> importables) {
        this.name = circuitName;
        this.components = components;
        this.inputs = inputs;
        this.outputs = outputs;
        this.innerWires = innerWires;
        this.importables = importables;
    }

    public Circuit(final String circuitName)
            throws IOException, InvalidLogicParametersException, FeedbackCircuitDetectedException {
        this.components = new ArrayList<>();
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.innerWires = new ArrayList<>();
        this.importables = new ArrayList<>();
        this.name = circuitName;

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

    public Scanner getCircuitScanner(final String circuitName) throws IOException {
        final String path = "saved_circuits/" + circuitName + ".txt";
        return new Scanner(Path.of(path));
    }

    public void parseImportLine(final String line) {
        final String[] split = line.split("\\s+");
        importables.addAll(Arrays.asList(Arrays.copyOfRange(split, 1, split.length)));
    }

    public void parseContactsLine(final String line) {
        final String[] split = line.split("\\s+");
        boolean isInput = true;
        for (final String s : split) {
            if (s.equals("->")) {
                isInput = false;
                continue;
            }

            final Wire wInner = new Wire(s);
            final Wire wOuter = new Wire(s);
            innerWires.add(wInner);

            Contact c;
            if (isInput) {
                c = new Contact(wOuter, wInner, true);
                inputs.add(c);
            } else {
                c = new Contact(wInner, wOuter, false);
                outputs.add(c);
            }
        }
    }

    public Optional<Wire> findWire(final String name) {
        for (final Wire w : innerWires) {
            if (w.getName().equals(name)) {
                return Optional.ofNullable(w);
            }
        }
        return Optional.empty();
    }

    public void hookUp(final List<Wire> inWires, final List<Wire> outWires)
            throws InvalidLogicParametersException {
        if (inWires.size() != inputs.size()) {
            throw new InvalidLogicParametersException(true, inputs.size(), inWires.size());
        }
        if (outWires.size() != outputs.size()) {
            throw new InvalidLogicParametersException(false, inputs.size(), outWires.size());
        }

        final Iterator<Contact> inputsIter = inputs.iterator();
        final Iterator<Wire> inWiresIter = inWires.iterator();

        while (inputsIter.hasNext() && inWiresIter.hasNext()) {
            final Contact c = inputsIter.next();
            final Wire w = inWiresIter.next();
            c.setIn(w);
        }

        final Iterator<Contact> outputsIter = outputs.iterator();
        final Iterator<Wire> outWiresIter = outWires.iterator();

        while (outputsIter.hasNext() && outWiresIter.hasNext()) {
            final Contact c = outputsIter.next();
            final Wire w = outWiresIter.next();
            c.setOut(w);
        }
    }

    private void parseNot(final List<String> split)
            throws InvalidLogicParametersException, FeedbackCircuitDetectedException {
        if (split.size() != 4) {
            final int arrowIndex = split.indexOf("->");
            if (arrowIndex != 2) {
                throw new InvalidLogicParametersException(true, 1, arrowIndex - 1);
            }
            throw new InvalidLogicParametersException(false, 1, split.size() - arrowIndex - 1);
        }

        final String inputWireName = split.get(1);
        final String outputWireName = split.get(3);
        final Wire input =
                findWire(inputWireName).orElseThrow(FeedbackCircuitDetectedException::new);
        Wire output;

        final Optional<Wire> outputOptional = findWire(outputWireName);
        if (outputOptional.isEmpty()) {
            output = new Wire(outputWireName);
            innerWires.add(output);
        } else {
            output = outputOptional.get();
        }

        components.add(new GateNot(input, output));
    }

    private void parseGate(final List<String> split, final int gateIndex)
            throws InvalidLogicParametersException {
        final int arrowIndex = split.indexOf("->");
        if (arrowIndex != split.size() - 2) {
            throw new InvalidLogicParametersException(false, 1, arrowIndex);
        }

        final List<Wire> ins = new ArrayList<>();
        for (int i = 1; i < arrowIndex; i += 1) {
            final String inputName = split.get(i);
            final Wire w = findWire(inputName).get();
            ins.add(w);
        }

        final String outputName = split.get(split.size() - 1);
        final Optional<Wire> outputOptional = findWire(outputName);
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
            default:
        }
    }

    public void parseComponentLine(final String line)
            throws IOException, InvalidLogicParametersException, FeedbackCircuitDetectedException {
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
        final Circuit c = new Circuit(componentType);
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
                innerWires.add(output);
            } else {
                outWires.add(w.get());
            }
        }
        c.hookUp(inWires, outWires);
        components.add(c);
    }

    @Override
    public void feed(final List<Signal> inSignals) throws InvalidLogicParametersException {
        if (inSignals.size() != inputs.size()) {
            throw new InvalidLogicParametersException(true, inputs.size(), inSignals.size());
        }

        final Iterator<Signal> signalsIter = inSignals.iterator();
        final Iterator<Contact> inputsIterator = inputs.iterator();

        while (signalsIter.hasNext() && inputsIterator.hasNext()) {
            final Signal s = signalsIter.next();
            final Contact c = inputsIterator.next();

            c.getIn().setSignal(s);
        }
    }

    @Override
    public void feedFromString(final String inSignals)
            throws InvalidLogicParametersException, MalformedSignalException {
        final List<Signal> signals = Signal.fromString(inSignals);
        feed(signals);
    }

    @Override
    public boolean propagate() {
        boolean changed = false;
        for (final Contact c : inputs) {
            final boolean result = c.propagate();
            changed |= result;
        }
        for (final Logic l : components) {
            final boolean result = l.propagate();
            changed |= result;
        }
        for (final Contact c : outputs) {
            final boolean result = c.propagate();
            changed |= result;
        }
        return changed;
    }

    @Override
    public List<Signal> read() {
        return outputs.stream().map(c -> c.getOut().getSignal()).toList();
    }

    @Override
    public List<Signal> inspect(final List<Signal> inputs) throws InvalidLogicParametersException {
        feed(inputs);
        propagate();
        return read();
    }

    @Override
    public String inspectFromString(final String inputs)
            throws InvalidLogicParametersException, MalformedSignalException {
        final List<Signal> i = Signal.fromString(inputs);
        return Signal.toString(inspect(i));
    }

    public static String indent(final String s) {
        return s.lines().map(line -> "  " + line + "\n").collect(Collectors.joining());
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append(this.name);
        s.append(" : ");
        s.append(inputs.stream().map(i -> i.getIn().toString()).collect(Collectors.joining()));
        s.append(" -> ");
        s.append(outputs.stream().map(o -> o.getOut().toString()).collect(Collectors.joining()));
        s.append("\n");
        for (final Logic component : components) {
            s.append(indent(component.toString()));
        }
        return s.toString();
    }

    public List<Logic> getComponents() {
        return components;
    }

    public void setComponents(final List<Logic> components) {
        this.components = components;
    }

    public List<Contact> getInputs() {
        return inputs;
    }

    public void setInputs(final List<Contact> inputs) {
        this.inputs = inputs;
    }

    public List<Contact> getOutputs() {
        return outputs;
    }

    public void setOutputs(final List<Contact> outputs) {
        this.outputs = outputs;
    }

    public List<Wire> getInnerWires() {
        return innerWires;
    }

    public void setInnerWires(final List<Wire> innerWires) {
        this.innerWires = innerWires;
    }

    public List<String> getImportables() {
        return importables;
    }

    public void setImportables(final List<String> importables) {
        this.importables = importables;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
