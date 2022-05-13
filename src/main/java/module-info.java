module edu.stevens.circuit.simulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires info.picocli;

    opens edu.stevens.circuit.simulator to javafx.fxml;
    exports edu.stevens.circuit.simulator;
}
