package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExceptionTests {
  // LogicMalformedSignal tests.
  @Test
  public void malformedSignal1() {
    LogicMalformedSignal e = new LogicMalformedSignal('c', "I didn't like that character.");
    assertTrue('c' == e.getBad());
  }

  @Test
  public void malformedSignal2() {
    LogicMalformedSignal e = new LogicMalformedSignal('Z', "short");
    e.setBad('q');
    e.setMsg("NEW MSG");
    assertTrue('q' == e.getBad());
    assertEquals("NEW MSG", e.getMsg());
  }

  @Test
  public void malformedSignal3() {
    LogicMalformedSignal e = new LogicMalformedSignal('Z', "short");
    try {
      throw e;
    } catch (LogicMalformedSignal ex) {
    }
  }
}
