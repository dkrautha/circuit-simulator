package edu.stevens.circuit.simulator;

import java.util.List;

public class GateXnor extends Gate {
    public GateXnor(final List<Wire> ins, final Wire out) throws InvalidLogicParametersException {
        super("XNOR", ins, out);
    }

    @Override
    public boolean propagate() {
        // TODO Auto-generated method stub
        return false;
    }

}
