package edu.stevens.circuit.simulator.circuit;

import java.util.List;

/**
 * Interface for a circuit.
 * 
 */
public interface Circuit {
    public void setInputs(List<Boolean> inputs) throws InvalidCircuitInput;

    public List<Boolean> calculateOutputs();

    public List<Boolean> getOutputs();
}
