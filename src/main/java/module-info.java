module view.gui.toylanguageinterpreter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens view.gui.toylanguageinterpreter to javafx.fxml;
    exports view.gui.toylanguageinterpreter;
}