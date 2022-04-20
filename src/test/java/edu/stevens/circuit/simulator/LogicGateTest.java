package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import edu.stevens.circuit.simulator.circuit.Circuit;
import edu.stevens.circuit.simulator.logic.gate.InvalidLogicGateInput;
import edu.stevens.circuit.simulator.logic.gate.LogicGate;
import edu.stevens.circuit.simulator.logic.gate.LogicGateType;

public class LogicGateTest {

    boolean[] f = {false};
    boolean[] t = {true};

    boolean[] ff = {false, false};
    boolean[] ft = {false, true};
    boolean[] tf = {true, false};
    boolean[] tt = {true, true};


    @Test
    public void test_NOT() {
        Circuit c = new LogicGate(LogicGateType.NOT);

        assertArrayEquals(f, c.outputs(t));
        assertArrayEquals(t, c.outputs(f));

        Exception e = assertThrows(InvalidLogicGateInput.class, () -> c.outputs(ff));
        assertEquals(e.getMessage(), "This logic gate requires one input.");
    }

    @Test
    public void test_AND() {
        Circuit c = new LogicGate(LogicGateType.NOT);

        assertArrayEquals(f, c.outputs(t));
        assertArrayEquals(t, c.outputs(f));
    }

    @Test()
    public void test_OR() {}

    @Test
    public void test_NAND() {}

    @Test
    public void test_NOR() {}

    @Test
    public void test_XOR() {}

    @Test
    public void test_XNOR() {}
}
