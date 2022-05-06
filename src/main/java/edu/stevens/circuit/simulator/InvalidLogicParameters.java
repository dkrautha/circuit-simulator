package edu.stevens.circuit.simulator;

/**
 * Exception to indicate that an incorrect number of parameters has been passed to a Logic.
 * 
 * Thrown when there are either not enough, or too many parameters for a Gate or Circuit.
 */
public class InvalidLogicParameters extends Exception {
    private boolean inputsRelated;
    private int expected;
    private int found;

    public InvalidLogicParameters(boolean inputsRelated, int expected, int found) {
        this.inputsRelated = inputsRelated;
        this.expected = expected;
        this.found = found;
    }

    @Override
    public String toString() {
        String related = inputsRelated ? "inputs" : "outputs";
        return String.format(
                "edu.stevens.circuit.simulator.InvalidLogicParameters: expected %d %s, found: %d %s",
                expected, related, found, related);
    }

    public boolean isInputsRelated() {
        return inputsRelated;
    }

    public void setInputsRelated(boolean inputsRelated) {
        this.inputsRelated = inputsRelated;
    }

    public int getExpected() {
        return expected;
    }

    public void setExpected(int expected) {
        this.expected = expected;
    }

    public int getFound() {
        return found;
    }

    public void setFound(int found) {
        this.found = found;
    }
}
