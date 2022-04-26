package edu.stevens.circuit.simulator.logic.gate;

import edu.stevens.circuit.simulator.circuit.InvalidCircuitInput;

public class InvalidLogicGateInput extends InvalidCircuitInput {
    public InvalidLogicGateInput(String message) {
        super(message);
    }
}
