package edu.stevens.circuit.simulator;

//should evaluate logic gates
//
public class gate {
    public int gateFunc;    //can take a value of 0 = not, 1 = or, 2 = and
    public gate(int gateFunc) {
        if (gateFunc == 0) {
            this.gateFunc = 0;
        }
        else if (gateFunc == 1) {
            this.gateFunc = 1;
        }
        else if (gateFunc == 2) {
            this.gateFunc = 2;
        }
        else {
            throw new InvalidGateType("Invalid Gate Type");
        }
    }

    public boolean gateEval(int gateFunc, boolean a, boolean b) {
        if (gateFunc == 0) {
            return !a;
        }
        else if (gateFunc == 1) {
            return a || b;
        }
        else {
            return a && b;
        }
    }
}

