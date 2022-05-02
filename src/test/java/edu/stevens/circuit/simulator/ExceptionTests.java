package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExceptionTests {
  // MalformedSignal tests.
  @Test
  public void malformedSignal1() {
    MalformedSignal e = new MalformedSignal('c', "I didn't like that character.");
    assertTrue('c' == e.getBad());
  }

  @Test
  public void malformedSignal2() {
    MalformedSignal e = new MalformedSignal('Z', "short");
    e.setBad('q');
    e.setMsg("NEW MSG");
    assertTrue('q' == e.getBad());
    assertEquals("NEW MSG", e.getMsg());
  }

  @Test
  public void malformedSignal3() {
    MalformedSignal e = new MalformedSignal('Z', "short");
    try {
      throw e;
    } catch (MalformedSignal ex) {
    }
  }

  // ExceptionLogicParameters tests.
  @Test()
  public void ELParams1() {
    InvalidLogicParameters e = new InvalidLogicParameters(true, 2, 4);
    assertEquals(true, e.isInputsRelated());
    assertEquals(2, e.getExpected());
    assertEquals(4, e.getFound());
  }

  @Test()
  public void ELParams2() {
    InvalidLogicParameters e = new InvalidLogicParameters(true, 2, 4);
    e.setInputsRelated(false);
    e.setExpected(5);
    e.setFound(3);
    assertEquals(false, e.isInputsRelated());
    assertEquals(5, e.getExpected());
    assertEquals(3, e.getFound());
  }

  @Test()
  public void ELParams3() {
    InvalidLogicParameters e = new InvalidLogicParameters(true, 2, 4);
    try {
      throw e;
    } catch (InvalidLogicParameters ex) {
    }
  }
}
