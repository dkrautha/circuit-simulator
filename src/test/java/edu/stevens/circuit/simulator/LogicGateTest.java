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
        Circuit c = new LogicGate(LogicGateType.AND);

        assertArrayEquals(f, c.outputs(ff));
        assertArrayEquals(f, c.outputs(ft));
        assertArrayEquals(f, c.outputs(tf));
        assertArrayEquals(t, c.outputs(tt));

        Exception e = assertThrows(InvalidLogicGateInput.class, () -> c.outputs(f));
        assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    }

    @Test()
    public void test_OR() {
        Circuit c = new LogicGate(LogicGateType.OR);

        assertArrayEquals(f, c.outputs(ff));
        assertArrayEquals(t, c.outputs(ft));
        assertArrayEquals(t, c.outputs(tf));
        assertArrayEquals(t, c.outputs(tt));

        Exception e = assertThrows(InvalidLogicGateInput.class, () -> c.outputs(f));
        assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    }

    @Test
    public void test_NAND() {
        Circuit c = new LogicGate(LogicGateType.NAND);

        assertArrayEquals(t, c.outputs(ff));
        assertArrayEquals(t, c.outputs(ft));
        assertArrayEquals(t, c.outputs(tf));
        assertArrayEquals(f, c.outputs(tt));

        Exception e = assertThrows(InvalidLogicGateInput.class, () -> c.outputs(f));
        assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    }

    @Test
    public void test_NOR() {
        Circuit c = new LogicGate(LogicGateType.NOR);

        assertArrayEquals(t, c.outputs(ff));
        assertArrayEquals(f, c.outputs(ft));
        assertArrayEquals(f, c.outputs(tf));
        assertArrayEquals(f, c.outputs(tt));

        Exception e = assertThrows(InvalidLogicGateInput.class, () -> c.outputs(f));
        assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    }

    @Test
    public void test_XOR() {
        Circuit c = new LogicGate(LogicGateType.XOR);

        assertArrayEquals(f, c.outputs(ff));
        assertArrayEquals(t, c.outputs(ft));
        assertArrayEquals(t, c.outputs(tf));
        assertArrayEquals(f, c.outputs(tt));

        Exception e = assertThrows(InvalidLogicGateInput.class, () -> c.outputs(f));
        assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    }

    @Test
    public void test_XNOR() {
        Circuit c = new LogicGate(LogicGateType.XNOR);

        assertArrayEquals(t, c.outputs(ff));
        assertArrayEquals(f, c.outputs(ft));
        assertArrayEquals(f, c.outputs(tf));
        assertArrayEquals(t, c.outputs(tt));

        Exception e = assertThrows(InvalidLogicGateInput.class, () -> c.outputs(f));
        assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    }
}
