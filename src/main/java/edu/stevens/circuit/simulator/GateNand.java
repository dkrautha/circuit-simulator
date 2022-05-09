package edu.stevens.circuit.simulator;

import java.util.List;

public class GateNand extends Gate {
    public GateNand(final List<Wire> ins, final Wire out) throws InvalidLogicParametersException {
        super("NAND", ins, out);
    }

    @Override
    public boolean propagate() {
        final Wire outWire = getOutput();
        final Signal oldSignal = outWire.getSignal();
        Signal newSignal = Signal.LO;

        for (final Wire w : getInputs()) {
            final Signal currentSignal = w.getSignal();
            if (currentSignal.equals(Signal.LO)) {
                newSignal = Signal.HI;
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
