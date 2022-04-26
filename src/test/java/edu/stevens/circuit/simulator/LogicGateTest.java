package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import edu.stevens.circuit.simulator.circuit.Circuit;
import edu.stevens.circuit.simulator.logic.gate.InvalidLogicGateInput;
import edu.stevens.circuit.simulator.logic.gate.LogicGate;
import edu.stevens.circuit.simulator.logic.gate.LogicGateType;

public class LogicGateTest {

    List<Boolean> f = Arrays.asList(false);
    List<Boolean> t = Arrays.asList(true);

    List<Boolean> ff = Arrays.asList(false, false);
    List<Boolean> ft = Arrays.asList(false, true);
    List<Boolean> tf = Arrays.asList(true, false);
    List<Boolean> tt = Arrays.asList(true, true);


    @Test
    public void test_NOT() {
        Circuit c = new LogicGate(LogicGateType.NOT);

        c.setInputs(t);
        assertEquals(f, c.calculateOutputs());

        c.setInputs(f);
        assertEquals(t, c.calculateOutputs());

        Exception e = assertThrows(InvalidLogicGateInput.class, () -> c.setInputs(ff));
        assertEquals("This logic gate requires one input.", e.getMessage());
    }

    // @Test
    // public void test_AND() {
    // Circuit c = new LogicGate(LogicGateType.AND);

    // c.setInputs(ff);
    // assertEquals(f, c.calculateOutputs());

    // c.setInputs(ft);
    // assertEquals(f, c.calculateOutputs());

    // c.setInputs(tf);
    // assertEquals(f, c.calculateOutputs());

    // c.setInputs(tt);
    // assertEquals(t, c.calculateOutputs());

    // Exception e = assertThrows(InvalidLogicGateInput.class, () -> c.calculateOutputs(f));
    // assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    // }

    // @Test()
    // public void test_OR() {
    // Circuit c = new LogicGate(LogicGateType.OR);

    // assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    // }

    // @Test
    // public void test_NAND() {
    // Circuit c = new LogicGate(LogicGateType.NAND);

    // assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    // }

    // @Test
    // public void test_NOR() {
    // Circuit c = new LogicGate(LogicGateType.NOR);

    // assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    // }

    // @Test
    // public void test_XOR() {
    // Circuit c = new LogicGate(LogicGateType.XOR);

    // assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    // }

    // @Test
    // public void test_XNOR() {
    // Circuit c = new LogicGate(LogicGateType.XNOR);

    // assertEquals(e.getMessage(), "This logic gate requires two inputs.");
    // }
}
