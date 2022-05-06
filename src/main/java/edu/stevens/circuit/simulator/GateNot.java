package edu.stevens.circuit.simulator;

import java.util.Arrays;

public class GateNot extends Gate {
    public GateNot(Wire input, Wire out) throws InvalidLogicParametersException {
        super("NOT", Arrays.asList(input), out);
    }

    @Override
    public boolean propagate() {
        Wire outWire = getOutput();
        Signal oldSignal = outWire.getSignal();
        Signal newSignal = getInputs().get(0).getSignal().invert();

        if (oldSignal.equals(newSignal)) {
            return false;
        }

        outWire.setSignal(newSignal);
        return true;
    }
}
