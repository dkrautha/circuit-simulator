package edu.stevens.circuit.simulator.circuit;

public class InvalidCircuitInput extends RuntimeException {
    public InvalidCircuitInput(String message) {
        super(message);
    }
}
