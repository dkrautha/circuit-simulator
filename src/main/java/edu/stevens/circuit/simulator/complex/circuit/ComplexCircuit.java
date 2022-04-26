package edu.stevens.circuit.simulator.complex.circuit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import edu.stevens.circuit.simulator.circuit.Circuit;
import edu.stevens.circuit.simulator.circuit.InvalidCircuitInput;
import edu.stevens.circuit.simulator.input.Input;

public class ComplexCircuit implements Circuit {
    public DefaultDirectedGraph<Circuit, DefaultEdge> circuitGraph;
    private List<Boolean> inputs;
    private List<Boolean> outputs;
    private int numberOfInputs = 0;

    public ComplexCircuit() {
        circuitGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
    }

    public boolean addCircuit(Circuit c) {
        if (c instanceof Input) {
            numberOfInputs += 1;
        }
        return circuitGraph.addVertex(c);
    }

    public void addConnection(Circuit source, Circuit target) {
        circuitGraph.addEdge(source, target);
    }


    @Override
    public List<Boolean> calculateOutputs() {
        Iterator<Circuit> graphIter = new DepthFirstIterator<>(circuitGraph);

        while (graphIter.hasNext()) {
            Circuit c = graphIter.next();
            if (!(c instanceof Input)) {
                List<Boolean> parentInputs = circuitGraph.edgesOf(c).stream()
                        .map(edge -> circuitGraph.getEdgeSource(edge))
                        .map(circuit -> circuit.getOutputs())
                        .collect(ArrayList::new, List::addAll, List::addAll);
                c.setInputs(parentInputs);
                outputs = c.calculateOutputs();
            }
        }

        return outputs;
    }

    @Override
    public List<Boolean> getOutputs() {
        return outputs;
    }

    @Override
    public void setInputs(List<Boolean> inputs) throws InvalidCircuitInput {
        if (inputs.size() != numberOfInputs) {
            throw new InvalidCircuitInput("messagaaae");
        }
        this.inputs = inputs;
    }

    @Override
    public String toString() {
        return circuitGraph.toString();
    }
}
