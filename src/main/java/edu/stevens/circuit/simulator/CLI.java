package edu.stevens.circuit.simulator;
import java.util.concurrent.Callable;

import java.util.List;
import java.io.IOException;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name="cirsim", mixinStandardHelpOptions = true, version = "cirsim 1.0",
            description = "Circuit Simulator")
public class CLI implements Callable<Integer>{
//     //index: the index of args
//    @Parameters(index="0",description="The file of circuit")
//    private String file;

//    //Instead of spece, the second arg will be splict by ","
//    //e.g.111,1x1,010,011
//    @Parameters(index = "1", description = "The signal of users", split = ",")
//    private String[] signals;
   
   @Option(names = {"-c","--create"},description="Create a new circuit")
   Boolean create;

   @Option(names = {"-p","--propgate"},description="Compute the outputs")
   Boolean propogate;

    @Option(names = {"-s","--signals"},description="Circuit inputs", split=",")
    String[] signals;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;
    
    @Option(names = {"-g","--gate"},description="Gates")
   String[] gates;

   @Parameters(index = "0..*", description = "The files of circuit.")
   String[] circuits;
    @Override
   public Integer call() throws Exception{
        // Circuit c = checkIfFeedbackCircuit(file);
        // for (String signal: signals){
        //     List<Signal> signals = Signal.fromString(signal);
        //     List<Signal> outputs = c.inspect(signals);
        //     System.out.println("Outputs: %s".formatted(outputs));
        // }
        // return 0;

        //e.g.-p -s 101,111,100,010 1 2 

        if (helpRequested){
            return 0;
        }
        if (propogate){
            for (String circuit:circuits){
                Circuit c = checkIfFeedbackCircuit(circuit);
                    for (String signal: signals){
                    List<Signal> signals = Signal.fromString(signal);
                    List<Signal> outputs = c.inspect(signals);
                    System.out.println("Outputs: %s".formatted(outputs));
                }
            }
            return 0;
        }

        return 0;
    }
   static Circuit checkIfFeedbackCircuit(String circuitName)
            throws IOException, InvalidLogicParametersException {
        try {
            return new Circuit(circuitName);
        } catch (FeedbackCircuitDetectedException e) {
            return new FeedbackCircuit(circuitName);
        }
    }
}
