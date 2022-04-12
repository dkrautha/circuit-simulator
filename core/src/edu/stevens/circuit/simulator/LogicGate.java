package edu.stevens.circuit.simulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

//Creates a "LogicGate" object, with an internal function, 2 inputs and 1 output
public class LogicGate {
  public boolean inputA;
  public boolean inputB;
  public gate func;
  public boolean output;
  // public boolean not(boolean a) { return !a; }
  // public boolean and(boolean a, boolean b) { return a && b; }
  // public boolean or(boolean a, boolean b) { return a || b; }
  // public boolean nand(boolean a, boolean b) { return !(a && b); }
  // public boolean nor(boolean a, boolean b) { return !(a || b); }
  // public boolean xor(boolean a, boolean b) { return a ^ b; }
  // public boolean xnor(boolean a, boolean b) { return !(a ^ b); }
  public LogicGate (
    boolean inputA,
    boolean inputB,
    gate func,
    boolean output
  ) {
    this.inputA = inputA;
    this.inputB = inputB;
    this.func = func;
    this.output = output;
  }

  static GateBuilder builder() {
    return new GateBuilder();
  }

  public static class GateBuilder {
    public boolean inputA;
    public boolean inputB;
    public gate func;
    public boolean output;

    public GateBuilder inputA(boolean inputA) {
      this.inputA = inputA;
      return this;
    }

    public GateBuilder inputB(boolean inputB) {
      this.inputB = inputB;
      return this;
    }

    public GateBuilder func(gate func) {
      this.func = func;
      return this;
    }

    public GateBuilder output(boolean output) {
      this.output = output;
      return this;
    }

    public LogicGate build() {
      return new LogicGate(inputA, inputB, func, output);
    }

  }
}
