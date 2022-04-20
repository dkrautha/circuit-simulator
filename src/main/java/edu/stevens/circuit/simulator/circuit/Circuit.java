package edu.stevens.circuit.simulator.circuit;

/**
 * Interface for a circuit.
 * 
 * Circuits will be the underlying data type for GraphNodes
 */
public interface Circuit {
    public boolean[] outputs(boolean[] inputs);
}
