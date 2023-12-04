package view.gui.toylanguageinterpreter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


// TODO: Semaphore
// TODO: Conditional assignment
// TODO: Lock + Unlock
// TODO: CountDownLatch
// TODO: Switch
// TODO: RepeatUntil
// TODO: Procedure
// TODO: Sleep
// TODO: CyclicBarrier
// TODO: Wait
// TODO: Relational Expression

public class GuiInterpreter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GuiInterpreter.class.getResource("select-window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(500);

        stage.setTitle("Toy Language Interpreter - Select Window");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}