package edu.stevens.circuit.simulator;

import java.util.Arrays;

public class GateNot extends Gate {
    public GateNot(final Wire input, final Wire out) throws InvalidLogicParametersException {
        super("NOT", Arrays.asList(input), out);
    }

    @Override
    public boolean propagate() {
        final Wire outWire = getOutput();
        final Signal oldSignal = outWire.getSignal();
        final Signal newSignal = getInputs().get(0).getSignal().invert();

        if (oldSignal.equals(newSignal)) {
            return false;
        }

        outWire.setSignal(newSignal);
        return true;
    }
}
