package edu.stevens.circuit.simulator;

public interface CircuitActions {
    void gateConnectA(LogicGate A, LogicGate B);
    void gateConnectB(LogicGate A, LogicGate B);
    LogicGate createGate(LogicGateType type);
}