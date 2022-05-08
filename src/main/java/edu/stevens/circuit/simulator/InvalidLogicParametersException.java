package edu.stevens.circuit.simulator;

/**
 * Exception to indicate that an incorrect number of parameters has been passed to a Logic.
 * 
 * Thrown when there are either not enough, or too many parameters for a Gate or Circuit.
 */
public class InvalidLogicParametersException extends Exception {
    private final boolean inputsRelated;
    private final int expected;
    private final int found;

    public InvalidLogicParametersException(final boolean inputsRelated, final int expected,
            final int found) {
        this.inputsRelated = inputsRelated;
        this.expected = expected;
        this.found = found;
    }

    @Override
    public String toString() {
        final String related = inputsRelated ? "inputs" : "outputs";
        return String.format(
                "edu.stevens.circuit.simulator.InvalidLogicParameters: expected %d %s, found: %d %s",
                expected, related, found, related);
    }

    public boolean isInputsRelated() {
        return inputsRelated;
    }

    public int getExpected() {
        return expected;
    }

    public int getFound() {
        return found;
    }
}
