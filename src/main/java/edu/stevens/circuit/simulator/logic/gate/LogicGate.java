package edu.stevens.circuit.simulator.logic.gate;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import edu.stevens.circuit.simulator.circuit.Circuit;

/**
 * Circuit representing the basic logic gates
 * 
 */
public class LogicGate implements Circuit {
    private final UnaryOperator<boolean[]> op;
    private final LogicGateType type;

    public LogicGate(LogicGateType type) {
        this.type = type;

        switch (type) {
            case AND:
                op = twoInOneOutOperator((a, b) -> a && b);
                break;
            case NAND:
                op = twoInOneOutOperator((a, b) -> !(a && b));
                break;
            case NOR:
                op = twoInOneOutOperator((a, b) -> !(a || b));
                break;
            case OR:
                op = twoInOneOutOperator((a, b) -> a || b);
                break;
            case XNOR:
                op = twoInOneOutOperator((a, b) -> ((a && b) || (!a && !b)));
                break;
            case XOR:
                op = twoInOneOutOperator((a, b) -> ((a && !b) || (!a && b)));
                break;
            case NOT:
                op = (inputs) -> {
                    if (inputs.length != 1) {
                        throw new InvalidLogicGateInput("This logic gate requires one input.");
                    }
                    boolean[] outputs = new boolean[1];
                    outputs[0] = !inputs[0];
                    return outputs;
                };
                break;
            default:
                throw new InvalidGateType();
        }
    }

    private UnaryOperator<boolean[]> twoInOneOutOperator(BinaryOperator<Boolean> func) {
        return (inputs) -> {
            if (inputs.length != 2) {
                throw new InvalidLogicGateInput("This logic gate requires two inputs.");
            }
            boolean[] outputs = new boolean[1];
            outputs[0] = func.apply(inputs[0], inputs[1]);
            return outputs;
        };
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LogicGate other = (LogicGate) obj;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public boolean[] outputs(boolean[] inputs) {
        return op.apply(inputs);
    }
}
