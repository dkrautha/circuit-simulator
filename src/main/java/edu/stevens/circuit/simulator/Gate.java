package edu.stevens.circuit.simulator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * The smallest unit of logic.
 * 
 * Although Gate is abstract, each of its concrete children represents a specific logic gate. There
 * is assumed to be nothing besides 1+ input wires, and a single output wire. Child classes should
 * only be responsible for overriding the propagate() method from Logic.
 */
public abstract class Gate implements Logic {
    private List<Wire> inputs;
    private Wire output;
    private String name;

    protected Gate(final String name, final List<Wire> ins, final Wire out)
            throws InvalidLogicParametersException {
        if (ins.isEmpty()) {
            throw new InvalidLogicParametersException(true, 1, ins.size());
        }

        this.name = name;
        this.inputs = ins;
        this.output = out;
    }

    @Override
    public void feed(final List<Signal> inSignals) throws InvalidLogicParametersException {
        if (inSignals.size() != inputs.size()) {
            throw new InvalidLogicParametersException(true, inputs.size(), inSignals.size());
        }

        final Iterator<Signal> sigIter = inSignals.iterator();
        final Iterator<Wire> wiresIter = inputs.iterator();

        while (sigIter.hasNext() && wiresIter.hasNext()) {
            final Signal s = sigIter.next();
            final Wire w = wiresIter.next();
            w.setSignal(s);
        }
    }

    @Override
    public void feedFromString(final String inSignals)
            throws InvalidLogicParametersException, MalformedSignalException {
        final List<Signal> signals = Signal.fromString(inSignals);
        feed(signals);
    }

    @Override
    public List<Signal> read() {
        return Arrays.asList(output.getSignal());
    }

    @Override
    public String toString() {
        return String.format("%s( %s | %s )", name, inputs.toString(), output.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputs, name, output);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Gate)) {
            return false;
        }
        final Gate other = (Gate) obj;
        return Objects.equals(inputs, other.inputs) && Objects.equals(name, other.name)
                && Objects.equals(output, other.output);
    }

    public List<Wire> getInputs() {
        return inputs;
    }

    public void setInputs(final List<Wire> inputs) {
        this.inputs = inputs;
    }

    public Wire getOutput() {
        return output;
    }

    public void setOutput(final Wire output) {
        this.output = output;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
