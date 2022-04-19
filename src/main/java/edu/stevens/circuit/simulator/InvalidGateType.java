package edu.stevens.circuit.simulator;

public class InvalidGateType extends RuntimeException {
    public InvalidGateType() {
        super("Invalid Gate Type passed, please use the GateType enum");
    }
}