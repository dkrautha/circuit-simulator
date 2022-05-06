package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ExceptionTests {
  // MalformedSignal tests.
  @Test
  public void malformedSignal1() {
    MalformedSignal e = new MalformedSignal('c');
    assertTrue('c' == e.getBad());
  }

  @Test
  public void malformedSignal2() {
    MalformedSignal e = new MalformedSignal('Z');
    e.setBad('q');
    assertTrue('q' == e.getBad());
    assertEquals(
        "edu.stevens.circuit.simulator.MalformedSignal: recieved a character 'q' that was not in \"01xX\"",
        e.toString());
  }

  @Test
  public void malformedSignal3() {
    MalformedSignal e = new MalformedSignal('Z');
    try {
      throw e;
    } catch (MalformedSignal ex) {
    }
  }

  // ExceptionLogicParameters tests.
  @Test()
  public void ELParams1() {
    InvalidLogicParametersException e = new InvalidLogicParametersException(true, 2, 4);
    assertEquals(true, e.isInputsRelated());
    assertEquals(2, e.getExpected());
    assertEquals(4, e.getFound());
  }

  @Test()
  public void ELParams2() {
    InvalidLogicParametersException e = new InvalidLogicParametersException(true, 2, 4);
    e.setInputsRelated(false);
    e.setExpected(5);
    e.setFound(3);
    assertEquals(false, e.isInputsRelated());
    assertEquals(5, e.getExpected());
    assertEquals(3, e.getFound());
  }

  @Test()
  public void ELParams3() {
    InvalidLogicParametersException e = new InvalidLogicParametersException(true, 2, 4);
    try {
      throw e;
    } catch (InvalidLogicParametersException ex) {
    }
  }
}
