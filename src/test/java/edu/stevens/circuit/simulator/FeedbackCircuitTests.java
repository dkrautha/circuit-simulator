package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FeedbackCircuitTests {
  public static void test_feedback_circuit(FeedbackCircuit c, String inputs, String expectedOutputs)
      throws MalformedSignal, InvalidLogicParametersException {
    List<Signal> sigIns = Signal.fromString(inputs);
    List<Signal> sigOuts = c.inspect(sigIns);
    String actualOuts = Signal.toString(sigOuts);
    assertEquals(expectedOutputs, actualOuts);
  }

  public static void test_feedback_circuit(String filename, String inputs, String expectedOutputs)
      throws MalformedSignal, InvalidLogicParametersException {
    try {
      test_feedback_circuit(new FeedbackCircuit(filename), inputs, expectedOutputs);
    } catch (IOException e) {
      fail("IOException when opening " + filename + " to read a Circuit." + e);
    }
  }

  // this is a "not(SR) NAND" latch circuit.
  // http://en.wikipedia.org/wiki/Flip-flop_(electronics)#SR_NAND_latch
  @Test
  public void file_fb_nSnR_nand() throws MalformedSignal, InvalidLogicParametersException {
    test_feedback_circuit("fb_nSnR_nand", "00", "11");
    test_feedback_circuit("fb_nSnR_nand", "01", "10");
    test_feedback_circuit("fb_nSnR_nand", "10", "01");
    test_feedback_circuit("fb_nSnR_nand", "11", "XX");
  }

  @Test
  public void file_fb_nSnR_nand_Xs() throws MalformedSignal, InvalidLogicParametersException {
    test_feedback_circuit("fb_nSnR_nand", "0X", "1X");
    test_feedback_circuit("fb_nSnR_nand", "1X", "XX");
    test_feedback_circuit("fb_nSnR_nand", "X0", "X1");
    test_feedback_circuit("fb_nSnR_nand", "X1", "XX");
    test_feedback_circuit("fb_nSnR_nand", "XX", "XX");
  }

  // this is an "SR NOR" latch circuit.
  // It has a "Set" and "Reset" wire. A high reset means reset Q=0 (and thus
  // nQ=1).
  // While reset stays low, if we put "set" to high, it sets Q=1 (and nQ=0).
  // returning "set" to low, Q stays at its previous value (Q=1 in the current
  // discussion).
  // not until we put "reset" high again will Q=0.
  //
  // http://en.wikipedia.org/wiki/Flip-flop_(electronics)#SR_NOR_latch
  @Test
  public void file_fb_SR_nor_v1()
      throws IOException, InvalidLogicParametersException, MalformedSignal {
    FeedbackCircuit fc = new FeedbackCircuit("fb_SR_nor");
    // reset the Q value.
    fc.feed(Signal.fromString("01"));
    fc.propagate();
    // should have Q=0 (and nQ=1).
    assertEquals("01", Signal.toString(fc.read()));
  }

  @Test
  public void file_fb_SR_nor_v2()
      throws IOException, InvalidLogicParametersException, MalformedSignal {
    FeedbackCircuit fc = new FeedbackCircuit("fb_SR_nor");
    // reset the Q value.
    fc.feed(Signal.fromString("01"));
    fc.propagate();
    // should have Q=0 (and nQ=1).
    assertEquals("01", Signal.toString(fc.read()));
    // feed S=0, R=0.
    fc.feed(Signal.fromString("00"));
    fc.propagate();
    // should still have Q=0 (and nQ=1).
    assertEquals("01", Signal.toString(fc.read()));
  }

  @Test
  public void file_fb_SR_nor_v3()
      throws IOException, InvalidLogicParametersException, MalformedSignal {
    FeedbackCircuit fc = new FeedbackCircuit("fb_SR_nor");
    // reset the Q value.
    fc.feed(Signal.fromString("01"));
    fc.propagate();
    // should have Q=0 (and nQ=1).
    assertEquals("01", Signal.toString(fc.read()));
    // feed S=0, R=0.
    fc.feed(Signal.fromString("00"));
    fc.propagate();
    // should still have Q=0 (and nQ=1).
    assertEquals("01", Signal.toString(fc.read()));
    // feed S=1, R=0.
    fc.feed(Signal.fromString("10"));
    fc.propagate();
    assertEquals("10", Signal.toString(fc.read()));
    fc.feed(Signal.fromString("00"));
    fc.propagate();
    assertEquals("10", Signal.toString(fc.read()));
    fc.feed(Signal.fromString("01"));
    fc.propagate();
    assertEquals("01", Signal.toString(fc.read()));
    fc.feed(Signal.fromString("00"));
    fc.propagate();
    assertEquals("01", Signal.toString(fc.read()));
  }

  @Test
  public void file_memory1_1()
      throws IOException, InvalidLogicParametersException, MalformedSignal {
    FeedbackCircuit fc = new FeedbackCircuit("memory1");
    assertEquals("1", fc.inspectFromString("11"));
    assertEquals("1", fc.inspectFromString("10"));
  }

  @Test
  public void file_memory1_2()
      throws IOException, InvalidLogicParametersException, MalformedSignal {
    FeedbackCircuit fc = new FeedbackCircuit("memory1");
    assertEquals("0", fc.inspectFromString("00"));
    assertEquals("0", fc.inspectFromString("10"));
  }

  @Test
  public void file_memory1_3()
      throws IOException, InvalidLogicParametersException, MalformedSignal {
    FeedbackCircuit fc = new FeedbackCircuit("memory1");
    assertEquals("0", fc.inspectFromString("00"));
    assertEquals("0", fc.inspectFromString("10"));
    assertEquals("1", fc.inspectFromString("11"));
    assertEquals("0", fc.inspectFromString("01"));
    assertEquals("0", fc.inspectFromString("00"));
    assertEquals("1", fc.inspectFromString("11"));
    assertEquals("0", fc.inspectFromString("01"));
  }

  @Test
  public void file_memory2_1()
      throws IOException, InvalidLogicParametersException, MalformedSignal {
    FeedbackCircuit fc = new FeedbackCircuit("memory2");
    assertEquals("1", fc.inspectFromString("11"));
    assertEquals("1", fc.inspectFromString("01"));
  }

  @Test
  public void file_memory2_2()
      throws IOException, InvalidLogicParametersException, MalformedSignal {
    FeedbackCircuit fc = new FeedbackCircuit("memory2");
    assertEquals("0", fc.inspectFromString("00"));
    assertEquals("0", fc.inspectFromString("01"));
  }
}
