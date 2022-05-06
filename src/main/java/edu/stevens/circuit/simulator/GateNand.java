package edu.stevens.circuit.simulator;

import java.util.List;

public class GateNand extends Gate {
    public GateNand(List<Wire> ins, Wire out) throws InvalidLogicParameters {
        super("NAND", ins, out);
    }

    @Override
    public boolean propagate() {
        Wire outWire = getOutput();
        Signal oldSignal = outWire.getSignal();
        Signal newSignal = Signal.LO;

        for (Wire w : getInputs()) {
            Signal currentSignal = w.getSignal();
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
