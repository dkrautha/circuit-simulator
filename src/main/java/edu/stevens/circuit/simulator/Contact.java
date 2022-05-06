package edu.stevens.circuit.simulator;

import java.util.Arrays;
import java.util.List;

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
    public void feed(List<Signal> inSignals) throws InvalidLogicParameters {
        if (inSignals.size() != 1) {
            throw new InvalidLogicParameters(true, 1, inSignals.size());
        }

        in.setSignal(inSignals.get(0));
    }

    @Override
    public void feedFromString(String inSignals) throws InvalidLogicParameters, MalformedSignal {
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
    public List<Signal> inspect(List<Signal> inputs) throws InvalidLogicParameters {
        feed(inputs);
        propagate();
        return read();
    }

    @Override
    public String inspectFromString(String inputs) throws InvalidLogicParameters, MalformedSignal {
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((in == null) ? 0 : in.hashCode());
        result = prime * result + (inbound ? 1231 : 1237);
        result = prime * result + ((out == null) ? 0 : out.hashCode());
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
        Contact other = (Contact) obj;
        if (in == null) {
            if (other.in != null)
                return false;
        } else if (!in.equals(other.in))
            return false;
        if (inbound != other.inbound)
            return false;
        if (out == null) {
            if (other.out != null)
                return false;
        } else if (!out.equals(other.out))
            return false;
        return true;
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
