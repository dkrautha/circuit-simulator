package edu.stevens.circuit.simulator;

/**
 * Exception to indicate that a signal cound not be formed from a textual input.
 */
public class LogicMalformedSignal extends RuntimeException {
    private char bad;
    private String msg;

    public LogicMalformedSignal(char bad, String msg) {
        this.bad = bad;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }

    public char getBad() {
        return bad;
    }

    public void setBad(char bad) {
        this.bad = bad;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
