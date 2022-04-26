package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import edu.stevens.circuit.simulator.circuit.Circuit;
import edu.stevens.circuit.simulator.complex.circuit.ComplexCircuit;
import edu.stevens.circuit.simulator.input.Input;
import edu.stevens.circuit.simulator.logic.gate.LogicGate;
import edu.stevens.circuit.simulator.logic.gate.LogicGateType;

public class ComplexCircuitTest {
    static ComplexCircuit rootCircuit = new ComplexCircuit();
    static Circuit input1 = new Input(Arrays.asList(true));
    static Circuit input2 = new Input(Arrays.asList(false));
    static Circuit orGate = new LogicGate(LogicGateType.OR);

    @BeforeEach
    public void init() {
        rootCircuit.addCircuit(input1);
        rootCircuit.addCircuit(input2);
        rootCircuit.addCircuit(orGate);

        rootCircuit.addConnection(input1, orGate);
        rootCircuit.addConnection(input2, orGate);
    }

    @Test
    public void test_taversal() {
        assertEquals(Arrays.asList(true), rootCircuit.calculateOutputs());
    }
}
