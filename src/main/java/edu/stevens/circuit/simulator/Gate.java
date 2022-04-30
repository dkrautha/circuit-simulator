package edu.stevens.circuit.simulator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

    public Gate(String name, List<Wire> ins, Wire out) {
        if (ins.isEmpty()) {
            throw new InvalidLogicParameters(true, 1, ins.size());
        }

        this.name = name;
        this.inputs = ins;
        this.output = out;
    }

    @Override
    public void feed(List<Signal> inSignals) {
        if (inSignals.size() != inputs.size()) {
            throw new InvalidLogicParameters(true, inputs.size(), inSignals.size());
        }

        Iterator<Signal> sigIter = inSignals.iterator();
        Iterator<Wire> wiresIter = inputs.iterator();

        while (sigIter.hasNext() && wiresIter.hasNext()) {
            Signal s = sigIter.next();
            Wire w = wiresIter.next();
            w.setSignal(s);
        }
    }

    @Override
    public void feedFromString(String inSignals) {
        List<Signal> signals = Signal.fromString(inSignals);
        feed(signals);
    }

    @Override
    public List<Signal> inspect(List<Signal> inputs) {
        feed(inputs);
        return read();
    }

    @Override
    public String inspectFromString(String inputs) {
        feedFromString(inputs);
        return read().toString();
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((inputs == null) ? 0 : inputs.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((output == null) ? 0 : output.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        System.out.println(getClass());
        System.out.println(obj.getClass());
        Gate other = (Gate) obj;
        if (inputs == null) {
            if (other.inputs != null)
                return false;
        } else if (!inputs.equals(other.inputs))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (output == null) {
            if (other.output != null)
                return false;
        } else if (!output.equals(other.output))
            return false;
        return true;
    }

    public List<Wire> getInputs() {
        return inputs;
    }

    public void setInputs(List<Wire> inputs) {
        this.inputs = inputs;
    }

    public Wire getOutput() {
        return output;
    }

    public void setOutput(Wire output) {
        this.output = output;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
