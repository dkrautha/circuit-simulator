package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ContactTests {
  // Contact tests.

  @Test
  public void contact_1() {
    Contact c = new Contact(new Wire("in"), new Wire("out"), true);
    assertEquals(new Wire("in"), c.getIn());
    assertEquals(new Wire("out"), c.getOut());
    assertEquals(true, c.isInbound());
  }

  @Test
  public void contact_2() {
    Contact c = new Contact(new Wire("in"), new Wire("out"), true);
    c.setIn(new Wire("in2"));

    assertFalse(new Wire("in").equals(c.getIn()));
    assertEquals(new Wire("in2"), c.getIn());

    c.setOut(new Wire("out2"));
    assertEquals(new Wire("out2"), c.getOut());

    c.setInbound(false);
    assertEquals(false, c.isInbound());
  }

  @Test
  public void contact_toString1() {
    assertEquals("A(B):X", new Contact(new Wire("A"), new Wire("B"), true).toString());
  }

  @Test
  public void contact_toString2() {
    assertEquals("(A)B:X", new Contact(new Wire("A"), new Wire("B"), false).toString());
  }

  @Test
  public void contact_toString3() {
    assertEquals("A:X", new Contact(new Wire("A"), new Wire("A"), true).toString());
    assertEquals("A:X", new Contact(new Wire("A"), new Wire("A"), false).toString());
  }

  @Test
  public void contact_toString4() {
    Wire a1 = new Wire("A");
    a1.setSignal(Signal.LO);
    Wire a2 = new Wire("A");
    a2.setSignal(Signal.LO);
    assertEquals("A:0", new Contact(a1, a2, true).toString());
  }

  @Test
  public void contact_toString5() {
    Wire a1 = new Wire("A");
    a1.setSignal(Signal.HI);
    Wire a2 = new Wire("A");
    a2.setSignal(Signal.HI);
    assertEquals("A:1", new Contact(a1, a2, true).toString());
  }

  @Test
  public void contact_equals() {
    Contact c1 = new Contact(new Wire("A"), new Wire("B"), true);
    Contact c2 = new Contact(new Wire("A"), new Wire("B"), true);
    Contact c3 = new Contact(new Wire("A"), new Wire("B"), false);
    Contact c4 = new Contact(new Wire("NO"), new Wire("nono!"), true);
    assertEquals(c1, c2);
    assertFalse(c1.equals(c3));
    assertFalse(c2.equals(c4));
    assertFalse(c4.equals(c1));
  }
}
