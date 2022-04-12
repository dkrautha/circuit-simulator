package edu.stevens.circuit.simulator;

public class LogicGates {
  public int numInputs;
  public gate func;
  public boolean output;
  //Creates a "gate" object, with an internal function, x inputs and 1 output
  // public boolean not(boolean a) { return !a; }
  // public boolean and(boolean a, boolean b) { return a && b; }
  // public boolean or(boolean a, boolean b) { return a || b; }
  // public boolean nand(boolean a, boolean b) { return !(a && b); }
  // public boolean nor(boolean a, boolean b) { return !(a || b); }
  // public boolean xor(boolean a, boolean b) { return a ^ b; }
  // public boolean xnor(boolean a, boolean b) { return !(a ^ b); }
  public LogicGates (
    int numInputs,
    gate func,
    boolean output
  ) {
    this.numInputs = numInputs;
    this.func = func;
    this.output = output;
  }

}
