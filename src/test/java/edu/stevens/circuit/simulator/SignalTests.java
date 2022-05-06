package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SignalTests {
  static List<Signal> sigs00, sigs01, sigs10, sigs11, sigs0X, sigsX0, sigs1X, sigsX1, sigsXX, sigs0,
      sigs1, sigsX;

  @BeforeAll
  public static void setupSignals() {
    sigs00 = Arrays.asList(new Signal[] {Signal.LO, Signal.LO});
    sigs01 = Arrays.asList(new Signal[] {Signal.LO, Signal.HI});
    sigs10 = Arrays.asList(new Signal[] {Signal.HI, Signal.LO});
    sigs11 = Arrays.asList(new Signal[] {Signal.HI, Signal.HI});
    sigs0X = Arrays.asList(new Signal[] {Signal.LO, Signal.X});
    sigsX0 = Arrays.asList(new Signal[] {Signal.X, Signal.LO});
    sigs1X = Arrays.asList(new Signal[] {Signal.HI, Signal.X});
    sigsX1 = Arrays.asList(new Signal[] {Signal.X, Signal.HI});
    sigsXX = Arrays.asList(new Signal[] {Signal.X, Signal.X});

    sigs0 = Arrays.asList(new Signal[] {Signal.LO});
    sigs1 = Arrays.asList(new Signal[] {Signal.HI});
    sigsX = Arrays.asList(new Signal[] {Signal.X});
  }

  @Test
  public void signal1() {
    assertEquals(Signal.HI, Signal.HI);
    assertEquals(Signal.LO, Signal.LO);
    assertEquals(Signal.X, Signal.X);
  }

  @Test
  public void signal2() {
    assertEquals("1", Signal.HI.toString());
  }

  @Test
  public void signal3() {
    assertEquals("0", Signal.LO.toString());
  }

  @Test
  public void signal4() {
    assertEquals("X", Signal.X.toString());
  }

  @Test
  public void signal5() throws MalformedSignal {
    assertEquals(Signal.HI, Signal.fromChar('1'));
  }

  @Test
  public void signal6() throws MalformedSignal {
    assertEquals(Signal.LO, Signal.fromChar('0'));
  }

  @Test
  public void signal7() throws MalformedSignal {
    assertEquals(Signal.X, Signal.fromChar('X'));
  }

  @Test
  public void signal8() throws MalformedSignal {
    assertEquals(Signal.X, Signal.fromChar('x'));
  }

  @Test
  public void signal9() {
    try {
      char c = ' ';
      Signal s = Signal.fromChar(c);
      fail(String.format("shouldn't have gotten back Signal %s from char'%s'.", s, c));
    } catch (MalformedSignal e) {
      return;
    }
  }

  @Test
  public void signal10() {
    try {
      char c = 'h';
      Signal s = Signal.fromChar(c);
      fail(String.format("shouldn't have gotten back Signal %s from char'%s'.", s, c));
    } catch (MalformedSignal e) {
      return;
    }
  }

  @Test
  public void signal11() throws MalformedSignal {
    String inp = "110X";
    List<Signal> actuals = Signal.fromString(inp);
    List<Signal> expecteds =
        Arrays.asList(new Signal[] {Signal.HI, Signal.HI, Signal.LO, Signal.X});
    assertEquals(expecteds, actuals);
  }

  @Test
  public void signal12() throws MalformedSignal {
    String inp = "";
    List<Signal> expecteds = Arrays.asList(new Signal[] {});
    List<Signal> actuals = Signal.fromString(inp);
    assertEquals(expecteds, actuals);
  }

  @Test
  public void signal13() throws MalformedSignal {
    String inp = "1 x \tX 00";
    List<Signal> expecteds =
        Arrays.asList(new Signal[] {Signal.HI, Signal.X, Signal.X, Signal.LO, Signal.LO});
    List<Signal> actuals = Signal.fromString(inp);
    assertEquals(expecteds, actuals);
  }

  @Test
  public void signal14() {
    try {
      String inp = "1 x \tX BAD characters ! 00";
      @SuppressWarnings("unused")
      List<Signal> expecteds = Signal.fromString(inp);
      fail("shouldn't have succeeded in reading past any bad characters.");
    } catch (MalformedSignal e) {
      return;
    }
  }

  @Test
  public void signal15() {
    List<Signal> originals = sigs0;
    assertEquals("0", Signal.toString(originals));
  }

  @Test
  public void signal16() {
    List<Signal> originals =
        Arrays.asList(new Signal[] {Signal.LO, Signal.HI, Signal.X, Signal.HI});
    assertEquals("01X1", Signal.toString(originals));
  }

  @Test
  public void signal17() {
    List<Signal> originals = Arrays.asList(new Signal[] {Signal.LO, Signal.HI, Signal.X, Signal.HI,
        Signal.LO, Signal.HI, Signal.X, Signal.HI});
    assertEquals("01X101X1", Signal.toString(originals));
  }
}
