package edu.stevens.circuit.simulator;

public class Circuit implements CircuitActions {
    //connect the output of A to inputA of B
    public void gateConnectA(LogicGate A, LogicGate B) {
        B.inputA = A.output;
    }
    //connect the output of A to inputB of B
    public void gateConnectB(LogicGate A, LogicGate B) {
        B.inputB = A.output;
    }
    public LogicGate createGate(LogicGateType type) {
        LogicGate gate = new LogicGate(type);
        return gate;
    }
}