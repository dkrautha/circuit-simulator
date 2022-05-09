package edu.stevens.circuit.simulator;

import java.util.List;

public class GateXor extends Gate {
    public GateXor(final List<Wire> ins, final Wire out) throws InvalidLogicParametersException {
        super("XOR", ins, out);
    }

    @Override
    public boolean propagate() {
        final Wire outWire = getOutput();
        final Signal oldSignal = outWire.getSignal();
        Signal newSignal = Signal.LO;
        int hiCount = 0;

        for (final Wire w : getInputs()) {
            final Signal currentSignal = w.getSignal();
            if (currentSignal.equals(Signal.X)) {
                newSignal = Signal.X;
                break;
            }
            if (currentSignal.equals(Signal.HI)) {
                hiCount += 1;
                if (hiCount == 1) {
                    newSignal = Signal.HI;
                } else {
                    newSignal = Signal.LO;
                }
            }
        }

        if (oldSignal.equals(newSignal)) {
            return false;
        }

        outWire.setSignal(newSignal);
        return true;
    }
}
