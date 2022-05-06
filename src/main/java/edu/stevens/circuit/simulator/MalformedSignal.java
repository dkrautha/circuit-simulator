package edu.stevens.circuit.simulator;

/**
 * Exception to indicate that a signal cound not be formed from a textual input.
 */
public class MalformedSignal extends Exception {
    private char bad;

    public MalformedSignal(char bad) {
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

    public void setBad(char bad) {
        this.bad = bad;
    }
}
