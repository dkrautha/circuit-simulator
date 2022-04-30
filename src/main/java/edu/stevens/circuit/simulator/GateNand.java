package edu.stevens.circuit.simulator;

import java.util.List;

public class GateNand extends Gate {
    public GateNand(List<Wire> ins, Wire out) {
        super("NAND", ins, out);
    }

    @Override
    public boolean propagate() {
        // TODO Auto-generated method stub
        return false;
    }

}
