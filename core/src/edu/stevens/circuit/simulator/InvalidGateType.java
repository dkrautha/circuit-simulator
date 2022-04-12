package edu.stevens.circuit.simulator;

public class InvalidGateType extends RuntimeException {
    public InvalidGateType(String errorMessage) {
        super(errorMessage);
    }
}
