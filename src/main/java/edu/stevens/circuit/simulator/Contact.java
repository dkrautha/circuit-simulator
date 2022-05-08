package edu.stevens.circuit.simulator;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Contact implements Logic {
    private Wire in;
    private Wire out;
    private boolean inbound;

    public Contact(Wire in, Wire out, boolean inbound) {
        this.in = in;
        this.out = out;
        this.inbound = inbound;
    }

    @Override
    public void feed(List<Signal> inSignals) throws InvalidLogicParametersException {
        if (inSignals.size() != 1) {
            throw new InvalidLogicParametersException(true, 1, inSignals.size());
        }

        in.setSignal(inSignals.get(0));
    }

    @Override
    public void feedFromString(String inSignals)
            throws InvalidLogicParametersException, MalformedSignalException {
        List<Signal> signals = Signal.fromString(inSignals);
        feed(signals);
    }

    @Override
    public boolean propagate() {
        Signal oldSignal = out.getSignal();
        Signal newSignal = in.getSignal();

        if (oldSignal.equals(newSignal)) {
            return false;
        }

        out.setSignal(newSignal);
        return true;
    }

    @Override
    public List<Signal> read() {
        return Arrays.asList(out.getSignal());
    }

    @Override
    public List<Signal> inspect(List<Signal> inputs) throws InvalidLogicParametersException {
        feed(inputs);
        propagate();
        return read();
    }

    @Override
    public String inspectFromString(String inputs)
            throws InvalidLogicParametersException, MalformedSignalException {
        feedFromString(inputs);
        propagate();
        return read().toString();
    }

    @Override
    public String toString() {
        if (in.getName().equals(out.getName())) {
            return in.toString();
        }
        if (inbound) {
            return String.format("%s(%s):%s", in.getName(), out.getName(), out.getSignal());
        }

        return String.format("(%s)%s", in.getName(), out);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(in, inbound, out);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Contact))
            return false;
        Contact other = (Contact) obj;
        return Objects.equals(in, other.in) && inbound == other.inbound
                && Objects.equals(out, other.out);
    }

    public Wire getIn() {
        return in;
    }

    public void setIn(Wire in) {
        this.in = in;
    }

    public Wire getOut() {
        return out;
    }

    public void setOut(Wire out) {
        this.out = out;
    }

    public boolean isInbound() {
        return inbound;
    }

    public void setInbound(boolean inbound) {
        this.inbound = inbound;
    }
}
