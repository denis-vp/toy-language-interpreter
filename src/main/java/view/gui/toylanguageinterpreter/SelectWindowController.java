package view.gui.toylanguageinterpreter;

import adt.*;
import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.ProgramState;
import programgenerator.ProgramGenerator;
import repository.IRepository;
import repository.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectWindowController implements Initializable {
    private final List<ProgramState> programs = new ArrayList<>();

    @FXML
    private ListView<String> selectProgramsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.createPrograms();
        this.populateSelectProgramsList();
    }

    private void createPrograms() {
        ProgramGenerator.getPrograms()
                .forEach(program -> {
                    ProgramState programState = new ProgramState(program, new MyStack<>(), new MyDictionary<>(),
                            new MyHeap(), new MyConcurrentDictionary<>(), new MyList<>(), new MyLockTable(), new MyLatchTable());
                    this.programs.add(programState);
                });
    }

    private void populateSelectProgramsList() {
        this.programs.forEach(program -> {
            this.selectProgramsList.getItems().add(program.getOriginalProgram().toString());
        });
    }

    public void switchToMainWindow(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GuiInterpreter.class.getResource("main-window.fxml"));

        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(500);

        stage.setTitle("Toy Language Interpreter - Main Window");
        stage.show();

        this.loadProgram(fxmlLoader);
    }

    private void loadProgram(FXMLLoader fxmlLoader) {
        int selectedIndex = this.selectProgramsList.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            selectedIndex = 0;
        }
        ProgramState program = this.programs.get(selectedIndex);
        IRepository repository = new Repository(program, "");
        Controller controller = new Controller(repository, false);

        MainWindowController mainWindowController = fxmlLoader.getController();
        mainWindowController.loadProgram(controller);
    }
}
