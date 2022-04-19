package edu.stevens.circuit.simulator;

import java.util.Optional;
import java.util.function.BinaryOperator;

public class LogicGate {
    final BinaryOperator<Boolean> op;
    final LogicGateType type;
    boolean inputA;
    boolean inputB;
    boolean output;

    public LogicGate(LogicGateType type) {
        this.type = type;

        switch (type) {
            case AND:
                op = (a, b) -> inputA && inputB;
                break;
            case NAND:
                op = (a, b) -> !(inputA && inputB);
                break;
            case NOR:
                op = (a, b) -> !(inputA || inputB);
                break;
            case NOT:
                op = null;
                break;
            case OR:
                op = (a, b) -> !(inputA || inputB);
                break;
            case XNOR:
                op = (a, b) -> ((inputA && inputB) || (!inputA && !inputB));
                break;
            case XOR:
                op = (a, b) -> ((inputA && !inputB) || (!inputA && inputB));
                break;
            case INPUT:
                op = null;
                break;
            default:
                throw new InvalidGateType();
        }

    }

    public boolean apply(Boolean inputA, Optional<Boolean> inputB) throws InvalidLogicGateInput {
        if (type == LogicGateType.NOT) {
            return !inputA;
        }

        if (type == LogicGateType.INPUT) {
            return inputA;
        }

        if (inputB.isEmpty()) {
            throw new InvalidLogicGateInput("Failed to provide a second input for non-NOT LogicGate");
        }

        return op.apply(inputA, inputB.get());
    }
}