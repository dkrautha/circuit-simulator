module edu.stevens.circuit.simulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jgrapht.core;
    requires transitive javafx.graphics;

    opens edu.stevens.circuit.simulator to javafx.fxml;
    exports edu.stevens.circuit.simulator;
}
