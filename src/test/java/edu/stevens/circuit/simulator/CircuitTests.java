package edu.stevens.circuit.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CircuitTests {
  List<Wire> wires1, wires2, wires3, wires4;

  @BeforeEach
  public void setupWires() {

    wires1 = Arrays.asList(new Wire[] {new Wire("a")});
    wires2 = Arrays.asList(new Wire[] {new Wire("a"), new Wire("b")});
    wires3 = Arrays.asList(new Wire[] {new Wire("a"), new Wire("b"), new Wire("c")});
    wires4 = Arrays.asList(new Wire[] {new Wire("a"), new Wire("b"), new Wire("c"), new Wire("d")});
  }

  List<Signal> sigs00, sigs01, sigs10, sigs11, sigs0X, sigsX0, sigs1X, sigsX1, sigsXX, sigs0, sigs1,
      sigsX;

  @BeforeEach
  public void setupSignals() {
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

  // Circuit tests

  List<Logic> componentsArg;
  List<Contact> inputContactsArg, outputContactsArg;
  List<Wire> innerWiresArg;
  List<String> importablesArg;
  Circuit simpleCircuit, simpleCircuit2, vanillaCircuit, andy;

  @BeforeEach
  public void prepareCircuits() throws InvalidLogicParametersException {
    Wire a = new Wire("A");
    Wire b = new Wire("B");
    Wire c = new Wire("C");
    Wire out = new Wire("out");
    Wire inner = new Wire("inner");
    GateOr gate_or = new GateOr(new ArrayList<Wire>(Arrays.asList(new Wire[] {a, b})), inner);
    GateAnd gate_and = new GateAnd(new ArrayList<Wire>(Arrays.asList(new Wire[] {inner, c})), out);

    componentsArg = new ArrayList<Logic>(Arrays.asList(new Logic[] {gate_or, gate_and}));

    Contact ca = new Contact(a, a, true);
    Contact cb = new Contact(b, b, true);
    Contact cc = new Contact(c, c, true);
    Contact cout = new Contact(out, out, false);
    inputContactsArg = new ArrayList<Contact>(Arrays.asList(new Contact[] {ca, cb, cc}));
    outputContactsArg = new ArrayList<Contact>(Arrays.asList(new Contact[] {cout}));

    innerWiresArg = new ArrayList<Wire>(Arrays.asList(new Wire[] {a, b, c, out, inner}));
    importablesArg = new ArrayList<String>();

    simpleCircuit = new Circuit("simpleCircuit", componentsArg, inputContactsArg, outputContactsArg,
        innerWiresArg, importablesArg);
    simpleCircuit2 = new Circuit("simpleCircuit2", componentsArg, inputContactsArg,
        outputContactsArg, innerWiresArg, importablesArg);

    vanillaCircuit = new Circuit("cname", new ArrayList<Logic>(), new ArrayList<Contact>(),
        new ArrayList<Contact>(), new ArrayList<Wire>(), new ArrayList<String>());
  }

  // this is equivalent to the samples/andy.txt circuit.
  public static Circuit getAndy() throws InvalidLogicParametersException {
    // andy circuit's wires
    Wire andyTemp = new Wire("temp");
    Wire andyOrary = new Wire("orary");
    Wire andyOut = new Wire("out");
    Wire andyTemp1 = new Wire("temp1");
    Wire andyTemp2 = new Wire("temp2");

    // first notnot's wires
    Wire n1in = new Wire("in");
    Wire n1out = new Wire("out");
    Wire n1temp = new Wire("temp");

    // second notnot's wires
    Wire n2in = new Wire("in");
    Wire n2out = new Wire("out");
    Wire n2temp = new Wire("temp");

    // first notnot's gates
    GateNot n1 = new GateNot(n1in, n1temp);
    GateNot n2 = new GateNot(n1temp, n1out);

    // secont notnot's gates
    GateNot n3 = new GateNot(n2in, n2temp);
    GateNot n4 = new GateNot(n2temp, n2out);

    // first notnot's contacts
    Contact n1c1 = new Contact(andyTemp, n1in, true);
    Contact n1c2 = new Contact(n1out, andyTemp1, false);

    // second notnot's contacts
    Contact n2c1 = new Contact(andyOrary, n2in, true);
    Contact n2c2 = new Contact(n2out, andyTemp2, false);

    // andy's contacts
    Contact ac1 = new Contact(andyTemp, andyTemp, true);
    Contact ac2 = new Contact(andyOrary, andyOrary, true);
    Contact ac3 = new Contact(andyOut, andyOut, false);

    Circuit notnot1 =
        new Circuit("notnot", new ArrayList<Logic>(Arrays.asList(new Logic[] {n1, n2})),
            new ArrayList<Contact>(Arrays.asList(new Contact[] {n1c1})),
            new ArrayList<Contact>(Arrays.asList(new Contact[] {n1c2})),
            new ArrayList<Wire>(Arrays.asList(new Wire[] {n1in, n1out, n1temp})),
            new ArrayList<String>());

    Circuit notnot2 = new Circuit("notnot", Arrays.asList(new Logic[] {n3, n4}),
        Arrays.asList(new Contact[] {n2c1}), Arrays.asList(new Contact[] {n2c2}),
        Arrays.asList(new Wire[] {n2in, n2out, n2temp}), Arrays.asList(new String[] {}));

    GateAnd innerAnd = new GateAnd(Arrays.asList(new Wire[] {andyTemp1, andyTemp2}), andyOut);

    Circuit andy = new Circuit("andy",
        new ArrayList<Logic>(Arrays.asList(new Logic[] {notnot1, notnot2, innerAnd})),
        new ArrayList<Contact>(Arrays.asList(new Contact[] {ac1, ac2})),
        new ArrayList<Contact>(Arrays.asList(new Contact[] {ac3})),
        new ArrayList<Wire>(
            Arrays.asList(new Wire[] {andyTemp, andyOrary, andyOut, andyTemp1, andyTemp2})),
        new ArrayList<String>(Arrays.asList(new String[] {"notnot"})));
    return andy;
  }

  @Test
  public void circuit_1() {
    simpleCircuit.setComponents(Arrays.asList(new Logic[] {}));
    assertEquals(Arrays.asList(new Logic[] {}), simpleCircuit.getComponents());
  }

  @Test
  public void circuit_2() {
    List<Contact> ins =
        Arrays.asList(new Contact[] {new Contact(new Wire("A"), new Wire("B"), true)});
    simpleCircuit.setInputs(ins);
    assertEquals(ins, simpleCircuit.getInputs());
  }

  @Test
  public void circuit_3() {
    List<Contact> outs =
        Arrays.asList(new Contact[] {new Contact(new Wire("A"), new Wire("B"), true)});
    simpleCircuit.setOutputs(outs);
    assertEquals(outs, simpleCircuit.getOutputs());
  }

  @Test
  public void circuit_4() {
    List<Wire> inners = Arrays.asList(new Wire[] {new Wire("X"), new Wire("Y"), new Wire("Z")});
    simpleCircuit.setInnerWires(inners);
    assertEquals(inners, simpleCircuit.getInnerWires());
  }

  @Test
  public void circuit_5() {
    List<String> imps = Arrays.asList(new String[] {"half", "full", "ripple"});
    simpleCircuit.setImportables(imps);
    assertEquals(imps, simpleCircuit.getImportables());
  }

  @Test
  public void circuit_6() {
    simpleCircuit.setName("newnamehere");
    assertEquals("newnamehere", simpleCircuit.getName());
  }

  @Test
  public void circuit_getCircuitScanner() throws IOException {
    Scanner sc = vanillaCircuit.getCircuitScanner("and");
    String expected = "A B -> out\n\nAND A B -> out";
    String found = sc.useDelimiter("\\Z").next(); // note, this removes the last newline.
    assertEquals(expected, found);
  }

  @Test
  public void circuit_parseImportLine1() {
    vanillaCircuit.parseImportLine("IMPORT a");
    assertEquals(Arrays.asList(new String[] {"a"}), vanillaCircuit.getImportables());
  }

  @Test
  public void circuit_parseImportLine2() {
    vanillaCircuit.parseImportLine("IMPORT a b c half full ripple4");
    assertEquals(Arrays.asList(new String[] {"a", "b", "c", "half", "full", "ripple4"}),
        vanillaCircuit.getImportables());
  }

  @Test
  public void circuit_parseContactsLine1() {
    List<Contact> empty = Arrays.asList(new Contact[] {});
    assertEquals(empty, vanillaCircuit.getInputs());
    assertEquals(empty, vanillaCircuit.getOutputs());
    List<Contact> ins =
        Arrays.asList(new Contact[] {new Contact(new Wire("A"), new Wire("A"), true),
            new Contact(new Wire("B"), new Wire("B"), true),
            new Contact(new Wire("C"), new Wire("C"), true)});
    List<Contact> outs =
        Arrays.asList(new Contact[] {new Contact(new Wire("D"), new Wire("D"), false)});

    vanillaCircuit.parseContactsLine("A B C -> D");
    assertEquals(ins, vanillaCircuit.getInputs());
    assertEquals(outs, vanillaCircuit.getOutputs());
  }

  @Test
  public void circuit_parseContactsLine2() {
    List<Contact> empty = Arrays.asList(new Contact[] {});
    assertEquals(empty, vanillaCircuit.getInputs());
    assertEquals(empty, vanillaCircuit.getOutputs());
    List<Contact> ins =
        Arrays.asList(new Contact[] {new Contact(new Wire("A"), new Wire("A"), true),
            new Contact(new Wire("B"), new Wire("B"), true),
            new Contact(new Wire("C"), new Wire("C"), true)});
    List<Contact> outs =
        Arrays.asList(new Contact[] {new Contact(new Wire("D"), new Wire("D"), false),
            new Contact(new Wire("E"), new Wire("E"), false)});

    List<Wire> inners = Arrays.asList(
        new Wire[] {new Wire("A"), new Wire("B"), new Wire("C"), new Wire("D"), new Wire("E")});
    vanillaCircuit.parseContactsLine("A B C -> D E");
    assertEquals(ins, vanillaCircuit.getInputs());
    assertEquals(outs, vanillaCircuit.getOutputs());
    assertEquals(inners, vanillaCircuit.getInnerWires());
  }

  @Test
  public void circuit_findWire1() {
    Wire f = simpleCircuit.findWire("A").get();
    assertEquals(new Wire("A"), f);
  }

  @Test
  public void circuit_findWire2() {
    Optional<Wire> f = simpleCircuit.findWire("NOTPRESENT");
    assertTrue(f.isEmpty());
  }

  @Test
  public void circuit_findWire3() {
    Wire f = simpleCircuit.findWire("inner").get();
    assertEquals(new Wire("inner"), f);
    f = simpleCircuit.findWire("out").get();
    assertEquals(new Wire("out"), f);
  }

  @Test
  public void circuit_findWire4() throws InvalidLogicParametersException {
    Optional<Wire> w = getAndy().findWire("temp");
    assertEquals(new Wire("temp"), w.get());
    w = getAndy().findWire("garbage");
    assertTrue(w.isEmpty());
  }

  @Test
  public void circuit_hookup1() throws InvalidLogicParametersException {
    assertEquals(Arrays.asList(new Contact[] {new Contact(new Wire("A"), new Wire("A"), true),
        new Contact(new Wire("B"), new Wire("B"), true),
        new Contact(new Wire("C"), new Wire("C"), true)}), simpleCircuit.getInputs());
    simpleCircuit.hookUp(
        Arrays.asList(new Wire[] {new Wire("newA"), new Wire("newB"), new Wire("newC")}),
        Arrays.asList(new Wire[] {new Wire("newOut")}));
    assertEquals(new Wire("newA"), simpleCircuit.getInputs().get(0).getIn());
    assertEquals(new Wire("newB"), simpleCircuit.getInputs().get(1).getIn());
    assertEquals(new Wire("newC"), simpleCircuit.getInputs().get(2).getIn());
    assertEquals(new Wire("newOut"), simpleCircuit.getOutputs().get(0).getOut());
  }

  @Test
  public void circuit_hookup2() {
    try {
      simpleCircuit.hookUp(Arrays.asList(new Wire[] {new Wire("newA")}), // too short!
          Arrays.asList(new Wire[] {new Wire("newOut")}));
      fail("should have complained about hookUp lengths.");
    } catch (InvalidLogicParametersException e) {
      assertEquals(3, e.getExpected());
      assertEquals(1, e.getFound());
      assertTrue(e.isInputsRelated());
    }
  }

  @Test
  public void circuit_parseComponentLine_1()
      throws IOException, InvalidLogicParametersException, FeedbackCircuitDetectedException {
    String line = "OR A B out -> brandNewWire";
    simpleCircuit.parseComponentLine(line);

    // did we get a new innerWires value added?
    assertEquals(6, simpleCircuit.getInnerWires().size());
    assertEquals(new Wire("brandNewWire"), simpleCircuit.getInnerWires().get(5));

    // did we get a new (third) component added? Previously, had an OR and an AND
    // gate in the list only.
    assertEquals(3, simpleCircuit.getComponents().size());
    assertTrue(simpleCircuit.getComponents().get(2) instanceof GateOr);
    assertEquals(Arrays.asList(new Wire[] {new Wire("A"), new Wire("B"), new Wire("out")}),
        ((GateOr) simpleCircuit.getComponents().get(2)).getInputs());
    assertEquals(new Wire("brandNewWire"),
        ((GateOr) simpleCircuit.getComponents().get(2)).getOutput());
  }

  @Test
  public void circuit_parseComponentLine_2()
      throws IOException, InvalidLogicParametersException, FeedbackCircuitDetectedException {
    String line = "notnot temp -> newOUT";
    Circuit andy = getAndy();
    andy.parseComponentLine(line);

    // did we get a new innerWires value added?
    assertEquals(6, andy.getInnerWires().size());
    assertEquals(new Wire("newOUT"), andy.getInnerWires().get(5));

    // did we get a new (third) component added? Previously, had an OR and an AND
    // gate in the list only.
    assertEquals(4, andy.getComponents().size());
    assertTrue(andy.getComponents().get(3) instanceof Circuit);
    assertEquals("notnot", ((Circuit) andy.getComponents().get(3)).getName());
    assertEquals(Arrays.asList(new Contact[] {new Contact(new Wire("temp"), new Wire("in"), true)}),
        ((Circuit) andy.getComponents().get(3)).getInputs());
    assertEquals(
        Arrays.asList(new Contact[] {new Contact(new Wire("out"), new Wire("newOUT"), false)}),
        ((Circuit) andy.getComponents().get(3)).getOutputs());
  }

  @Test
  public void circuit_parseComponentLine_3() throws IOException, FeedbackCircuitDetectedException {
    String line = "OR -> brandNewWire";
    try {
      simpleCircuit.parseComponentLine(line);
      fail("should have complained that OR receieved no arguments.");
    } catch (InvalidLogicParametersException e) {
    }
  }

  @Test
  public void circuit_feed1() throws InvalidLogicParametersException {
    simpleCircuit.feed(Arrays.asList(new Signal[] {Signal.HI, Signal.LO, Signal.X}));
    assertEquals(Signal.HI, simpleCircuit.getInputs().get(0).getIn().getSignal());
    assertEquals(Signal.LO, simpleCircuit.getInputs().get(1).getIn().getSignal());
    assertEquals(Signal.X, simpleCircuit.getInputs().get(2).getIn().getSignal());
  }

  @Test
  public void circuit_feed2() throws InvalidLogicParametersException, MalformedSignalException {
    simpleCircuit.feedFromString("10X");
    assertEquals(Signal.HI, simpleCircuit.getInputs().get(0).getIn().getSignal());
    assertEquals(Signal.LO, simpleCircuit.getInputs().get(1).getIn().getSignal());
    assertEquals(Signal.X, simpleCircuit.getInputs().get(2).getIn().getSignal());
  }

  @Test
  public void circuit_feed3() throws MalformedSignalException {
    try {
      simpleCircuit.feedFromString("10X1");
      fail("shouldn't have succeeded in feeding four signals to a three-input circuit.");
    } catch (InvalidLogicParametersException e) {
      return;
    }
  }

  @Test
  public void circuit_propagate1()
      throws InvalidLogicParametersException, MalformedSignalException {
    simpleCircuit.feedFromString("101");
    simpleCircuit.propagate();
    assertEquals(Signal.HI, simpleCircuit.getOutputs().get(0).getOut().getSignal());
  }

  @Test
  public void circuit_propagate2()
      throws InvalidLogicParametersException, MalformedSignalException {
    simpleCircuit.feedFromString("101");
    boolean ans = simpleCircuit.propagate();
    assertTrue(ans);
    ans = simpleCircuit.propagate();
    assertFalse(ans);
  }

  @Test
  public void circuit_propagate3()
      throws InvalidLogicParametersException, MalformedSignalException {
    simpleCircuit.feedFromString("110");
    simpleCircuit.propagate();
    assertEquals(Signal.LO, simpleCircuit.getOutputs().get(0).getOut().getSignal());
  }

  @Test
  public void circuit_propagate4()
      throws InvalidLogicParametersException, MalformedSignalException {
    simpleCircuit.feedFromString("X00");
    simpleCircuit.propagate();
    assertEquals(Signal.LO, simpleCircuit.getOutputs().get(0).getOut().getSignal());
  }

  @Test
  public void circuit_propagate5()
      throws InvalidLogicParametersException, MalformedSignalException {
    simpleCircuit.feedFromString("10X");
    simpleCircuit.propagate();
    assertEquals(Signal.X, simpleCircuit.getOutputs().get(0).getOut().getSignal());
  }

  @Test
  public void circuit_propagate6()
      throws InvalidLogicParametersException, MalformedSignalException {
    Circuit andy = getAndy();
    andy.feedFromString("00");
    andy.propagate();
    assertEquals(Signal.LO, andy.getOutputs().get(0).getOut().getSignal());
  }

  @Test
  public void circuit_propagate7()
      throws InvalidLogicParametersException, MalformedSignalException {
    Circuit andy = getAndy();
    andy.feedFromString("10");
    andy.propagate();
    assertEquals(Signal.LO, andy.getOutputs().get(0).getOut().getSignal());
    andy.feedFromString("01");
    andy.propagate();
    assertEquals(Signal.LO, andy.getOutputs().get(0).getOut().getSignal());
  }

  @Test
  public void circuit_propagate8()
      throws InvalidLogicParametersException, MalformedSignalException {
    Circuit andy = getAndy();
    andy.feedFromString("11");
    andy.propagate();
    assertEquals(Signal.HI, andy.getOutputs().get(0).getOut().getSignal());
  }

  @Test
  public void circuit_propagate9()
      throws InvalidLogicParametersException, MalformedSignalException {
    Circuit andy = getAndy();
    andy.feedFromString("0X");
    andy.propagate();
    assertEquals(Signal.LO, andy.getOutputs().get(0).getOut().getSignal());
    andy.feedFromString("X0");
    andy.propagate();
    assertEquals(Signal.LO, andy.getOutputs().get(0).getOut().getSignal());
    andy.feedFromString("XX");
    andy.propagate();
    assertEquals(Signal.X, andy.getOutputs().get(0).getOut().getSignal());
  }

  @Test
  public void circuit_read1() throws InvalidLogicParametersException, MalformedSignalException {
    simpleCircuit.feedFromString("101");
    simpleCircuit.propagate();
    assertEquals(sigs1, simpleCircuit.read());
  }

  @Test
  public void circuit_read2() throws InvalidLogicParametersException, MalformedSignalException {
    simpleCircuit.feedFromString("110");
    simpleCircuit.propagate();
    assertEquals(sigs0, simpleCircuit.read());
  }

  @Test
  public void circuit_read3() throws InvalidLogicParametersException, MalformedSignalException {
    simpleCircuit.feedFromString("X00");
    simpleCircuit.propagate();
    assertEquals(sigs0, simpleCircuit.read());
  }

  @Test
  public void circuit_read4() throws InvalidLogicParametersException, MalformedSignalException {
    simpleCircuit.feedFromString("10X");
    simpleCircuit.propagate();
    assertEquals(sigsX, simpleCircuit.read());
  }

  @Test
  public void circuit_read5() throws InvalidLogicParametersException, MalformedSignalException {
    Circuit andy = getAndy();
    andy.feedFromString("00");
    andy.propagate();
    assertEquals(sigs0, andy.read());
  }

  @Test
  public void circuit_read6() throws InvalidLogicParametersException, MalformedSignalException {
    Circuit andy = getAndy();
    andy.feedFromString("10");
    andy.propagate();
    assertEquals(sigs0, andy.read());
    andy.feedFromString("01");
    andy.propagate();
    assertEquals(sigs0, andy.read());
  }

  @Test
  public void circuit_read7() throws InvalidLogicParametersException, MalformedSignalException {
    Circuit andy = getAndy();
    andy.feedFromString("11");
    andy.propagate();
    assertEquals(sigs1, andy.read());
  }

  @Test
  public void circuit_read8() throws InvalidLogicParametersException, MalformedSignalException {
    Circuit andy = getAndy();
    andy.feedFromString("0X");
    andy.propagate();
    assertEquals(sigs0, andy.read());
    andy.feedFromString("X0");
    andy.propagate();
    assertEquals(sigs0, andy.read());
    andy.feedFromString("XX");
    andy.propagate();
    assertEquals(sigsX, andy.read());
  }

  public void circuit_andy()
      throws IOException, InvalidLogicParametersException, FeedbackCircuitDetectedException {
    Circuit studentAndy = new Circuit("andy");
    Circuit handCodedAndy = getAndy();

    assertEquals(handCodedAndy.getInputs(), studentAndy.getInputs());
    assertEquals(handCodedAndy.getOutputs(), studentAndy.getOutputs());
    assertEquals(handCodedAndy.getInnerWires(), studentAndy.getInnerWires());
    assertEquals(handCodedAndy.getImportables(), studentAndy.getImportables());
    assertEquals(handCodedAndy.getName(), studentAndy.getName());

    // don't have .equals() for circuits, use string representation.
    assertEquals(handCodedAndy.getComponents().toString(), studentAndy.getComponents().toString());
  }

  @Test
  public void circuit_indent1() {
    String sub = "a b c\nd!\n";
    assertEquals("  a b c\n  d!\n", Circuit.indent(sub));
  }

  @Test
  public void circuit_indent2() {
    String sub = "  a\n    b\n  c\n";
    assertEquals("    a\n      b\n    c\n", Circuit.indent(sub));
  }

  @Test
  public void circuit_toString()
      throws IOException, InvalidLogicParametersException, FeedbackCircuitDetectedException {
    assertEquals(new Circuit("andy").toString(), getAndy().toString());
  }
}
