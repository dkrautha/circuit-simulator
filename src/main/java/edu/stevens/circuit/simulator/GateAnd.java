package edu.stevens.circuit.simulator;

import java.util.List;

public class GateAnd extends Gate {
    public GateAnd(final List<Wire> ins, final Wire out) throws InvalidLogicParametersException {
        super("AND", ins, out);
    }

    @Override
    public boolean propagate() {
        final Wire outWire = getOutput();
        final Signal oldSignal = outWire.getSignal();
        Signal newSignal = Signal.HI;

        for (final Wire w : getInputs()) {
            final Signal currentSignal = w.getSignal();
            if (currentSignal.equals(Signal.LO)) {
                newSignal = Signal.LO;
                break;
            }
            if (currentSignal.equals(Signal.X)) {
                newSignal = Signal.X;
            }
        }

        if (oldSignal.equals(newSignal)) {
            return false;
        }

        outWire.setSignal(newSignal);
        return true;
    }
}
