package edu.stevens.circuit.simulator;

import java.util.List;

public class GateNor extends Gate {
    public GateNor(final List<Wire> ins, final Wire out) throws InvalidLogicParametersException {
        super("NOR", ins, out);
    }

    @Override
    public boolean propagate() {
        final Wire outWire = getOutput();
        final Signal oldSignal = outWire.getSignal();
        Signal newSignal = Signal.HI;

        for (final Wire w : getInputs()) {
            final Signal currentSignal = w.getSignal();
            if (currentSignal.equals(Signal.HI)) {
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
