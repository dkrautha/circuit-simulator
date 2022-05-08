package edu.stevens.circuit.simulator;

import java.util.Objects;

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
        return Objects.hash(name, signal);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Wire)) {
            return false;
        }
        Wire other = (Wire) obj;
        return Objects.equals(name, other.name) && signal == other.signal;
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
