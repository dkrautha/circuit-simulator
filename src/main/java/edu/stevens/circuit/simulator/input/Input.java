package edu.stevens.circuit.simulator.input;

import java.util.Arrays;
import java.util.List;
import edu.stevens.circuit.simulator.circuit.Circuit;
import edu.stevens.circuit.simulator.circuit.InvalidCircuitInput;

public class Input implements Circuit {
    private List<Boolean> input;

    public Input(List<Boolean> input) {
        this.input = input;
    }

    public void setInput(List<Boolean> input) {
        this.input = input;
    }

    @Override
    public List<Boolean> calculateOutputs() {
        return this.input;
    }

    @Override
    public List<Boolean> getOutputs() {
        return input;
    }

    @Override
    public void setInputs(List<Boolean> inputs) throws InvalidCircuitInput {
        if (inputs.size() != 1) {
            throw new InvalidInputInput(
                    "Input circuits do not take an input for the outputs function.");
        }

        this.input = inputs;
    }
}
