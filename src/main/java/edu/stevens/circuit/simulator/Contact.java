package edu.stevens.circuit.simulator;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Contact implements Logic {
    private Wire in;
    private Wire out;
    private boolean inbound;

    public Contact(final Wire in, final Wire out, final boolean inbound) {
        this.in = in;
        this.out = out;
        this.inbound = inbound;
    }

    @Override
    public void feed(final List<Signal> inSignals) throws InvalidLogicParametersException {
        if (inSignals.size() != 1) {
            throw new InvalidLogicParametersException(true, 1, inSignals.size());
        }

        in.setSignal(inSignals.get(0));
    }

    @Override
    public void feedFromString(final String inSignals)
            throws InvalidLogicParametersException, MalformedSignalException {
        final List<Signal> signals = Signal.fromString(inSignals);
        feed(signals);
    }

    @Override
    public boolean propagate() {
        final Signal oldSignal = out.getSignal();
        final Signal newSignal = in.getSignal();

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
    public List<Signal> inspect(final List<Signal> inputs) throws InvalidLogicParametersException {
        feed(inputs);
        propagate();
        return read();
    }

    @Override
    public String inspectFromString(final String inputs)
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

    @Override
    public int hashCode() {
        return Objects.hash(in, inbound, out);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Contact)) {
            return false;
        }
        final Contact other = (Contact) obj;
        return Objects.equals(in, other.in) && inbound == other.inbound
                && Objects.equals(out, other.out);
    }

    public Wire getIn() {
        return in;
    }

    public void setIn(final Wire in) {
        this.in = in;
    }

    public Wire getOut() {
        return out;
    }

    public void setOut(final Wire out) {
        this.out = out;
    }

    public boolean isInbound() {
        return inbound;
    }

    public void setInbound(final boolean inbound) {
        this.inbound = inbound;
    }
}
