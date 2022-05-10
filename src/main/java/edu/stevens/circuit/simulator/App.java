package edu.stevens.circuit.simulator;

// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

// /**
// * JavaFX App
// */
// public class App extends Application {
// private static Scene scene;

// @Override
// public void start(Stage stage) throws IOException {
// scene = new Scene(loadFXML("primary"), 640, 480);
// stage.setScene(scene);
// stage.show();
// }

// static void setRoot(String fxml) throws IOException {
// scene.setRoot(loadFXML(fxml));
// }

// private static Parent loadFXML(String fxml) throws IOException {
// FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
// return fxmlLoader.load();
// }

// public static void main(String[] args) {
// launch();
// }
// }

public class App {
    static Circuit checkIfFeedbackCircuit(String circuitName)
            throws IOException, InvalidLogicParametersException {
        try {
            return new Circuit(circuitName);
        } catch (FeedbackCircuitDetectedException e) {
            return new FeedbackCircuit(circuitName);
        }
    }

    public static void main(String[] args) {
        // TODO: allow the user to pass in multiple sets of input signals
        if (args.length != 2) {
            System.out.println(
                    "This program takes two CLI arguments, the first specifies the circuit file to open and run, the second is a string of inputs.");
            return;
        }

        String circuitName = args[0];
        try {
            List<Signal> signals = Signal.fromString(args[1]);
            Circuit c = checkIfFeedbackCircuit(circuitName);
            List<Signal> outputs = c.inspect(signals);
            System.out.println("Outputs: %s".formatted(outputs));
        } catch (IOException | MalformedSignalException | InvalidLogicParametersException e) {
            e.printStackTrace();
        }
    }
}
