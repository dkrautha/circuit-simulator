package edu.stevens.circuit.simulator;

/**
 * Represents the connectors between gates and circuits.
 * 
 * Provides an easy way to name the connections between things.
 */
public class Wire {
    private Signal signal;
    private String name;

    public Wire(String name) {
        this.name = name;
        this.signal = Signal.X;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", name, signal.toString());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((signal == null) ? 0 : signal.hashCode());
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
        Wire other = (Wire) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (signal != other.signal)
            return false;
        return true;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
