package edu.stevens.circuit.simulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;


@Command(name = "circsim", mixinStandardHelpOptions = true, version = "circuit-simulator 0.1.0",
        description = "A program for simulating circuits made from basic logic gates.")
public class App implements Callable<Integer> {
    @Parameters(index = "0")
    String circuitName;

    @Parameters(index = "1..*")
    List<String> inputStrings;


    static Circuit checkIfFeedbackCircuit(String circuitName)
            throws IOException, InvalidLogicParametersException {
        try {
            return new Circuit(circuitName);
        } catch (FeedbackCircuitDetectedException e) {
            return new FeedbackCircuit(circuitName);
        }
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new App()).execute(args));
    }

    @Override
    public Integer call() throws Exception {
        Circuit c = checkIfFeedbackCircuit(circuitName);
        List<List<Signal>> signalsToTry = new ArrayList<>();
        for (String s : inputStrings) {
            List<Signal> signals = Signal.fromString(s);
            signalsToTry.add(signals);
        }

        for (List<Signal> signals : signalsToTry) {
            List<Signal> outputs = c.inspect(signals);
            System.out.println("For input: %s\nGot output: %s".formatted(signals, outputs));
        }

        return 0;
    }
}
