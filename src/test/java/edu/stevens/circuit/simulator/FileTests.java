package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileTests {
  public static boolean assertOutsMatch(List<Wire> outs, List<Signal> sigs) {
    if (outs.size() != sigs.size()) {
      fail(String.format("wrong number of output wires (%d) vs. signals (%d).", outs.size(),
          sigs.size()));
    }
    for (int i = 0; i < outs.size(); i++) {
      if (outs.get(i).getSignal() != sigs.get(i))
        return false;
    }
    return true;
  }

  public static void test_circuit(Circuit c, String inputs, String expectedOutputs)
      throws InvalidLogicParametersException, MalformedSignalException {
    List<Signal> sigIns = Signal.fromString(inputs);
    List<Signal> sigOuts = c.inspect(sigIns);
    String actualOuts = Signal.toString(sigOuts);
    assertEquals(expectedOutputs, actualOuts);
  }

  public static void test_circuit(String filename, String inputs, String expectedOutputs)
      throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    try {
      test_circuit(new Circuit(filename), inputs, expectedOutputs);
    } catch (IOException e) {
      fail("IOException when opening " + filename + " to read a Circuit." + e);
    }
  }

  @Test
  public void file_not_0() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("not", "0", "1");
  }

  @Test
  public void file_not_1() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("not", "1", "0");
  }

  @Test
  public void file_not_X() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("not", "X", "X");
  }

  @Test
  public void file_and_00() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("and", "00", "0");
  }

  @Test
  public void file_and_01() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("and", "01", "0");
  }

  @Test
  public void file_and_10() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("and", "10", "0");
  }

  @Test
  public void file_and_11() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("and", "11", "1");
  }

  @Test
  public void file_and_2_Xs() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("and", "0X", "0");
    test_circuit("and", "X0", "0");
    test_circuit("and", "1X", "X");
    test_circuit("and", "X1", "X");
    test_circuit("and", "XX", "X");
  }

  @Test
  public void file_and_single() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("and1", "0", "0");
    test_circuit("and1", "1", "1");
    test_circuit("and1", "X", "X");
  }

  @Test
  public void file_and_3s() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("and_3in", "000", "0");
    test_circuit("and_3in", "001", "0");
    test_circuit("and_3in", "010", "0");
    test_circuit("and_3in", "011", "0");
    test_circuit("and_3in", "100", "0");
    test_circuit("and_3in", "101", "0");
    test_circuit("and_3in", "110", "0");
    test_circuit("and_3in", "111", "1");
  }

  @Test
  public void file_and_3_Xs() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("and_3in", "00X", "0");
    test_circuit("and_3in", "01X", "0");
    test_circuit("and_3in", "0X0", "0");
    test_circuit("and_3in", "0X1", "0");
    test_circuit("and_3in", "0XX", "0");
    test_circuit("and_3in", "10X", "0");
    test_circuit("and_3in", "11X", "X");
    test_circuit("and_3in", "1X0", "0");
    test_circuit("and_3in", "1X1", "X");
    test_circuit("and_3in", "1XX", "X");
    test_circuit("and_3in", "X00", "0");
    test_circuit("and_3in", "X01", "0");
    test_circuit("and_3in", "X0X", "0");
    test_circuit("and_3in", "X10", "0");
    test_circuit("and_3in", "X11", "X");
    test_circuit("and_3in", "X1X", "X");
    test_circuit("and_3in", "XX0", "0");
    test_circuit("and_3in", "XX1", "X");
    test_circuit("and_3in", "XXX", "X");
  }

  @Test
  public void file_or() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("or", "00", "0");
    test_circuit("or", "01", "1");
    test_circuit("or", "10", "1");
    test_circuit("or", "11", "1");
  }

  @Test
  public void file_or_Xs() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("or", "0X", "X");
    test_circuit("or", "X0", "X");
    test_circuit("or", "1X", "1");
    test_circuit("or", "X1", "1");
    test_circuit("or", "XX", "X");
  }

  @Test
  public void file_xor() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("xor", "00", "0");
    test_circuit("xor", "01", "1");
    test_circuit("xor", "10", "1");
    test_circuit("xor", "11", "0");
  }

  @Test
  public void file_xor_Xs() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("xor", "0X", "X");
    test_circuit("xor", "X0", "X");
    test_circuit("xor", "1X", "X");
    test_circuit("xor", "X1", "X");
    test_circuit("xor", "XX", "X");
  }

  @Test
  public void file_nand() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("nand", "00", "1");
    test_circuit("nand", "01", "1");
    test_circuit("nand", "10", "1");
    test_circuit("nand", "11", "0");
  }

  @Test
  public void file_nand_Xs() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("nand", "0X", "1");
    test_circuit("nand", "X0", "1");
    test_circuit("nand", "1X", "X");
    test_circuit("nand", "X1", "X");
    test_circuit("nand", "XX", "X");
  }

  @Test
  public void file_nor() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("nor", "00", "1");
    test_circuit("nor", "01", "0");
    test_circuit("nor", "10", "0");
    test_circuit("nor", "11", "0");
  }

  @Test
  public void file_nor_Xs() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("nor", "0X", "X");
    test_circuit("nor", "X0", "X");
    test_circuit("nor", "1X", "0");
    test_circuit("nor", "X1", "0");
    test_circuit("nor", "XX", "X");
  }

  @Test
  public void file_1() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("1", "000", "0");
    test_circuit("1", "001", "1");
    test_circuit("1", "010", "0");
    test_circuit("1", "011", "1");
    test_circuit("1", "100", "0");
    test_circuit("1", "101", "1");
    test_circuit("1", "110", "1");
    test_circuit("1", "111", "1");
  }

  @Test
  public void file_1_xs() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("1", "00X", "X");
    test_circuit("1", "0X0", "0");
    test_circuit("1", "X00", "0");
    test_circuit("1", "01X", "X");
    test_circuit("1", "0X1", "1");
    test_circuit("1", "X01", "1");
    test_circuit("1", "10X", "X");
    test_circuit("1", "1X0", "X");
    test_circuit("1", "X10", "X");
    test_circuit("1", "11X", "1");
    test_circuit("1", "1X1", "1");
    test_circuit("1", "X11", "1");
    test_circuit("1", "0XX", "X");
    test_circuit("1", "1XX", "X");
    test_circuit("1", "X0X", "X");
    test_circuit("1", "X1X", "X");
    test_circuit("1", "XX0", "X");
    test_circuit("1", "XX1", "1");
    test_circuit("1", "XXX", "X");
  }

  @Test
  public void file_2() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("2", "000", "0");
    test_circuit("2", "001", "0");
    test_circuit("2", "010", "0");
    test_circuit("2", "011", "1");
    test_circuit("2", "100", "0");
    test_circuit("2", "101", "1");
    test_circuit("2", "110", "0");
    test_circuit("2", "111", "1");
  }

  @Test
  public void file_2_Xs() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("2", "00X", "0");
    test_circuit("2", "0X0", "0");
    test_circuit("2", "X00", "0");
    test_circuit("2", "01X", "X");
    test_circuit("2", "0X1", "X");
    test_circuit("2", "X01", "X");
    test_circuit("2", "10X", "X");
    test_circuit("2", "1X0", "0");
    test_circuit("2", "X10", "0");
    test_circuit("2", "11X", "X");
    test_circuit("2", "1X1", "1");
    test_circuit("2", "X11", "1");
    test_circuit("2", "0XX", "X");
    test_circuit("2", "1XX", "X");
    test_circuit("2", "X0X", "X");
    test_circuit("2", "X1X", "X");
    test_circuit("2", "XX0", "0");
    test_circuit("2", "XX1", "X");
    test_circuit("2", "XXX", "X");
  }

  @Test
  public void file_halfadder() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("halfadder", "00", "00");
    test_circuit("halfadder", "01", "01");
    test_circuit("halfadder", "10", "01");
    test_circuit("halfadder", "11", "10");
  }

  @Test
  public void file_halfadder_Xs() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("halfadder", "0X", "0X");
    test_circuit("halfadder", "X0", "0X");
    test_circuit("halfadder", "1X", "XX");
    test_circuit("halfadder", "X1", "XX");
    test_circuit("halfadder", "XX", "XX");
  }

  @Test
  public void file_fulladder_basic() throws InvalidLogicParametersException,
      MalformedSignalException, FeedbackCircuitDetectedException {
    test_circuit("fulladder_basic", "000", "00");
    test_circuit("fulladder_basic", "001", "01");
    test_circuit("fulladder_basic", "010", "01");
    test_circuit("fulladder_basic", "011", "10");
    test_circuit("fulladder_basic", "100", "01");
    test_circuit("fulladder_basic", "101", "10");
    test_circuit("fulladder_basic", "110", "10");
    test_circuit("fulladder_basic", "111", "11");
  }

  @Test
  public void file_fulladder_basic_Xs() throws InvalidLogicParametersException,
      MalformedSignalException, FeedbackCircuitDetectedException {
    test_circuit("fulladder_basic", "00X", "0X");
    test_circuit("fulladder_basic", "0X0", "0X");
    test_circuit("fulladder_basic", "X00", "0X");
    test_circuit("fulladder_basic", "01X", "XX");
    test_circuit("fulladder_basic", "0X1", "XX");
    test_circuit("fulladder_basic", "X01", "XX");
    test_circuit("fulladder_basic", "10X", "XX");
    test_circuit("fulladder_basic", "1X0", "XX");
    test_circuit("fulladder_basic", "X10", "XX");
    test_circuit("fulladder_basic", "11X", "1X");
    test_circuit("fulladder_basic", "1X1", "XX");
    test_circuit("fulladder_basic", "X11", "XX");
    test_circuit("fulladder_basic", "0XX", "XX");
    test_circuit("fulladder_basic", "1XX", "XX");
    test_circuit("fulladder_basic", "X0X", "XX");
    test_circuit("fulladder_basic", "X1X", "XX");
    test_circuit("fulladder_basic", "XX0", "XX");
    test_circuit("fulladder_basic", "XX1", "XX");
    test_circuit("fulladder_basic", "XXX", "XX");
  }

  @Test
  public void file_fulladder() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("fulladder", "000", "00");
    test_circuit("fulladder", "001", "01");
    test_circuit("fulladder", "010", "01");
    test_circuit("fulladder", "011", "10");
    test_circuit("fulladder", "100", "01");
    test_circuit("fulladder", "101", "10");
    test_circuit("fulladder", "110", "10");
    test_circuit("fulladder", "111", "11");
  }

  @Test
  public void file_fulladder_Xs() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("fulladder", "00X", "0X");
    test_circuit("fulladder", "0X0", "0X");
    test_circuit("fulladder", "X00", "0X");
    test_circuit("fulladder", "01X", "XX");
    test_circuit("fulladder", "0X1", "XX");
    test_circuit("fulladder", "X01", "XX");
    test_circuit("fulladder", "10X", "XX");
    test_circuit("fulladder", "1X0", "XX");
    test_circuit("fulladder", "X10", "XX");
    test_circuit("fulladder", "11X", "1X");
    test_circuit("fulladder", "1X1", "XX");
    test_circuit("fulladder", "X11", "XX");
    test_circuit("fulladder", "0XX", "XX");
    test_circuit("fulladder", "1XX", "XX");
    test_circuit("fulladder", "X0X", "XX");
    test_circuit("fulladder", "X1X", "XX");
    test_circuit("fulladder", "XX0", "XX");
    test_circuit("fulladder", "XX1", "XX");
    test_circuit("fulladder", "XXX", "XX");
  }

  @Test
  public void file_mux_4_to_1() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("mux_4_to_1", "1000 00", "1");
    test_circuit("mux_4_to_1", "0111 00", "0");
    test_circuit("mux_4_to_1", "0100 10", "1");
    test_circuit("mux_4_to_1", "1011 10", "0");
    test_circuit("mux_4_to_1", "0010 01", "1");
    test_circuit("mux_4_to_1", "1101 01", "0");
    test_circuit("mux_4_to_1", "0001 11", "1");
    test_circuit("mux_4_to_1", "1110 11", "0");
  }

  @Test
  public void file_mux_4_to_1_Xs() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("mux_4_to_1", "X000 00", "X");
    test_circuit("mux_4_to_1", "X111 00", "X");
    test_circuit("mux_4_to_1", "0X00 10", "X");
    test_circuit("mux_4_to_1", "1X11 10", "X");
    test_circuit("mux_4_to_1", "00X0 01", "X");
    test_circuit("mux_4_to_1", "000X 11", "X");
    test_circuit("mux_4_to_1", "XXXX 01", "X");
    test_circuit("mux_4_to_1", "0000 XX", "0");
    test_circuit("mux_4_to_1", "1111 XX", "X");
  }

  @Test
  public void file_ripple4() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("ripple4", "0000 0000 0", "00000");
    test_circuit("ripple4", "0100 1100 0", "10100");
    test_circuit("ripple4", "0100 1100 1", "01100");
    test_circuit("ripple4", "1110 1001 0", "00001");
    test_circuit("ripple4", "1110 1001 1", "10001");
  }

  @Test
  public void file_nand_derived() throws InvalidLogicParametersException, MalformedSignalException,
      FeedbackCircuitDetectedException {
    test_circuit("nand_derived", "00", "1");
    test_circuit("nand_derived", "01", "1");
    test_circuit("nand_derived", "10", "1");
    test_circuit("nand_derived", "11", "0");
  }

  @Test
  public void file_nand_derived_Xs() throws InvalidLogicParametersException,
      MalformedSignalException, FeedbackCircuitDetectedException {
    test_circuit("nand_derived", "0X", "1");
    test_circuit("nand_derived", "X0", "1");
    test_circuit("nand_derived", "1X", "X");
    test_circuit("nand_derived", "X1", "X");
    test_circuit("nand_derived", "XX", "X");
  }
}
