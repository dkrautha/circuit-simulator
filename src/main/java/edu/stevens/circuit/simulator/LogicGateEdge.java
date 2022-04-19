package edu.stevens.circuit.simulator;

import java.util.Objects;

public class LogicGateEdge {
    private int id;

    public LogicGateEdge(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LogicGateEdge other = (LogicGateEdge) obj;
        return id == other.id;
    }
}
