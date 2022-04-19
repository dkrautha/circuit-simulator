package edu.stevens.circuit.simulator;

import java.util.Objects;

public class LogicGateNode {
    private int id;
    private LogicGate gate;

    public LogicGateNode(int id, LogicGate gate) {
        this.id = id;
        this.gate = gate;
    }

    public int getId() {
        return id;
    }

    public LogicGate getGate() {
        return gate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LogicGateNode other = (LogicGateNode) obj;
        return id == other.id && gate.equals(other.gate);
    }


}
