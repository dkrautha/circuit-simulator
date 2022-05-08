package edu.stevens.circuit.simulator;

/**
 * Exception to indicate that a signal cound not be formed from a textual input.
 */
public class MalformedSignalException extends Exception {
    private final char bad;

    public MalformedSignalException(final char bad) {
        this.bad = bad;
    }

    @Override
    public String toString() {
        return String.format(
                "edu.stevens.circuit.simulator.MalformedSignal: recieved a character '%c' that was not in \"01xX\"",
                bad);
    }

    public char getBad() {
        return bad;
    }
}
