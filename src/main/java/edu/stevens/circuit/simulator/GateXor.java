package edu.stevens.circuit.simulator;

import java.util.List;

public class GateXor extends Gate {
    public GateXor(List<Wire> ins, Wire out) {
        super("XOR", ins, out);
    }

    @Override
    public boolean propagate() {
        Wire outWire = getOutput();
        Signal oldSignal = outWire.getSignal();
        Signal newSignal = Signal.LO;
        int hiCount = 0;

        for (Wire w : getInputs()) {
            Signal currentSignal = w.getSignal();
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
