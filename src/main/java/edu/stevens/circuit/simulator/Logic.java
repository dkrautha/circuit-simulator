package edu.stevens.circuit.simulator;

import java.util.List;

public interface Logic {
    public void feed(List<Signal> inSignals);

    public void feed(String inSignals);

    public boolean propagate();

    public List<Signal> read();

    public List<Signal> inspect(List<Signal> inputs);

    public String inspect(String inputs);
}
