package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ExceptionTests {
  // MalformedSignal tests.
  @Test
  public void malformedSignal1() {
    MalformedSignalException e = new MalformedSignalException('c');
    assertTrue('c' == e.getBad());
  }

  @Test
  public void malformedSignal2() {
    MalformedSignalException e = new MalformedSignalException('Z');
    assertEquals(
        "edu.stevens.circuit.simulator.MalformedSignal: recieved a character 'Z' that was not in \"01xX\"",
        e.toString());
  }

  @Test()
  public void invalidLogicParameters1() {
    InvalidLogicParametersException e = new InvalidLogicParametersException(true, 2, 4);
    assertEquals(true, e.isInputsRelated());
    assertEquals(2, e.getExpected());
    assertEquals(4, e.getFound());
  }
}
