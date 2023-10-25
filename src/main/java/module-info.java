module com.example.kursfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.jetbrains.annotations;

    opens com.example.kursfx to javafx.fxml;
    exports com.example.kursfx;
    exports model;
    opens model to javafx.fxml;
}