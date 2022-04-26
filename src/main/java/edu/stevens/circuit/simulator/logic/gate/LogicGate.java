package edu.stevens.circuit.simulator.logic.gate;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import edu.stevens.circuit.simulator.circuit.Circuit;
import edu.stevens.circuit.simulator.circuit.InvalidCircuitInput;

/**
 * Circuit representing the basic logic gates
 * 
 */
public class LogicGate implements Circuit {
    private final UnaryOperator<List<Boolean>> op;
    public final LogicGateType type;
    private List<Boolean> inputs;
    private List<Boolean> outputs;
    private int numberOfInputs;

    public LogicGate(LogicGateType type) {
        this.type = type;
        outputs = Arrays.asList(false);
        numberOfInputs = 2;

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
                    Boolean result = !inputs.get(0);
                    outputs.set(0, result);
                    return outputs;
                };
                numberOfInputs = 1;
                break;
            default:
                throw new InvalidGateType();
        }
    }

    private UnaryOperator<List<Boolean>> twoInOneOutOperator(BinaryOperator<Boolean> func) {
        return (inputs) -> {
            Boolean result = func.apply(inputs.get(0), inputs.get(1));
            outputs.set(0, result);
            return outputs;
        };
    }

    @Override
    public List<Boolean> calculateOutputs() {
        return op.apply(inputs);
    }

    @Override
    public List<Boolean> getOutputs() {
        return outputs;
    }

    @Override
    public void setInputs(List<Boolean> inputs) throws InvalidCircuitInput {
        if (inputs.size() != numberOfInputs) {
            String message = "This logic gate requires two inputs.";
            if (type == LogicGateType.NOT) {
                message = "This logic gate requires one input.";
            }
            throw new InvalidLogicGateInput(message);
        }
        this.inputs = inputs;
    }
}
