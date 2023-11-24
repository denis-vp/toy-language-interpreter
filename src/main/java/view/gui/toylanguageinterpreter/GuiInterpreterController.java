package view.gui.toylanguageinterpreter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GuiInterpreterController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}