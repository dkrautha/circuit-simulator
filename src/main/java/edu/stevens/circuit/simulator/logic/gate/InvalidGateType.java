package edu.stevens.circuit.simulator.logic.gate;

public class InvalidGateType extends RuntimeException {
    public InvalidGateType() {
        super("Invalid Gate Type passed, please use the GateType enum");
    }
}