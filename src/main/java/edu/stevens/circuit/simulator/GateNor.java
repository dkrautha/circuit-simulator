package edu.stevens.circuit.simulator;

import java.util.List;

public class GateNor extends Gate {
    public GateNor(List<Wire> ins, Wire out) {
        super("NOR", ins, out);
    }

    @Override
    public boolean propagate() {
        // TODO Auto-generated method stub
        return false;
    }

}
