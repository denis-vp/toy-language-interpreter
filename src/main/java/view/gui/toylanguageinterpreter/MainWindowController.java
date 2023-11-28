package view.gui.toylanguageinterpreter;

import controller.Controller;
import exception.ControllerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ProgramState;
import model.statement.Statement;
import model.value.Value;
import view.gui.toylanguageinterpreter.tableentries.HeapTableEntry;
import view.gui.toylanguageinterpreter.tableentries.SymbolTableEntry;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private Controller program;
    String selectedThread;
    @FXML
    private ListView<String> threadsList;
    @FXML
    private ListView<String> executionStackList;
    @FXML
    private TableView<SymbolTableEntry> symbolTable;
    @FXML
    private TableColumn<SymbolTableEntry, String> symbolTableIdColumn;
    @FXML
    private TableColumn<SymbolTableEntry, String> symbolTableValueColumn;
    @FXML
    private ListView<String> outputList;
    @FXML
    private ListView<String> fileTableList;
    @FXML
    private TableView<HeapTableEntry> heapTable;
    @FXML
    private TableColumn<HeapTableEntry, String> addressHeapTableColumn;
    @FXML
    private TableColumn<HeapTableEntry, String> valueHeapTableColumn;
    @FXML
    private TextField threadCountText;

    public void loadProgram(Controller programController) {
        this.program = programController;
        this.updateWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.symbolTableIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.symbolTableValueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        this.addressHeapTableColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        this.valueHeapTableColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    }

    public void updateWindow() {
        this.populateThreadsList();
        this.populateExecutionStackList();
        this.populateSymbolTable();
        this.populateOutputList();
        this.populateFileTable();
        this.populateHeapTable();
        this.populateThreadCountText();
    }

    private void populateThreadsList() {
        int selectedIndex = this.threadsList.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            selectedIndex = 0;
        }

        this.threadsList.getItems().clear();
        this.program.getRepository().getProgramStateList()
                .forEach(programState -> {
                    this.threadsList.getItems().add(String.valueOf(programState.getId()));
                });

        this.threadsList.getSelectionModel().select(selectedIndex);
        this.selectedThread = this.threadsList.getItems().get(selectedIndex);
    }

    private void populateExecutionStackList() {
        this.executionStackList.getItems().clear();
        for (ProgramState programState : this.program.getRepository().getProgramStateList()) {
            if (String.valueOf(programState.getId()).equals(this.selectedThread)) {
                for (Statement statement : programState.getExecutionStack().reversed()) {
                    this.executionStackList.getItems().add(statement.toString());
                }
                break;
            }
        }
    }

    private void populateSymbolTable() {
        this.symbolTable.getItems().clear();
        for (ProgramState programState : this.program.getRepository().getProgramStateList()) {
            if (String.valueOf(programState.getId()).equals(this.selectedThread)) {
                for (String key : programState.getSymbolTable().keys()) {
                    SymbolTableEntry entry = new SymbolTableEntry(key, programState.getSymbolTable().get(key));
                    this.symbolTable.getItems().add(entry);
                }
                break;
            }
        }
    }

    private void populateOutputList() {
        this.outputList.getItems().clear();
        ProgramState programState = this.program.getRepository().getProgramStateList().get(0);
        for (Value output : programState.getOutput().values()) {
            this.outputList.getItems().add(output.toString());
        }
    }

    private void populateFileTable() {
        this.fileTableList.getItems().clear();
        ProgramState programState = this.program.getRepository().getProgramStateList().get(0);
        for (String file : programState.getFileTable().keys()) {
            this.fileTableList.getItems().add(file);
        }
    }

    private void populateHeapTable() {
        this.heapTable.getItems().clear();
        for (ProgramState programState : this.program.getRepository().getProgramStateList()) {
            if (String.valueOf(programState.getId()).equals(this.selectedThread)) {
                for (Integer address : programState.getHeap().keys()) {
                    HeapTableEntry entry = new HeapTableEntry(address, programState.getHeap().get(address));
                    this.heapTable.getItems().add(entry);
                }
                break;
            }
        }
    }

    private void populateThreadCountText() {
        String threadCountText = "Thread Count: ";
        threadCountText += this.program.getRepository().getProgramStateList().size();
        this.threadCountText.setText(threadCountText);
    }

    public void switchToSelectWindow(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GuiInterpreter.class.getResource("select-window.fxml"));

        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(500);

        stage.setTitle("Toy Language Interpreter - Select Window");
        stage.show();
    }

    public void oneStep() {
        List<ProgramState> programStates = this.program.removeCompletedPrograms(
                this.program.getRepository().getProgramStateList());

        if (programStates.isEmpty()) {
            this.program.getExecutor().shutdownNow();
            return;
        }

        this.program.getRepository().getHeap().setContent(
                this.program.garbageCollector(
                        this.program.getAddressesFromSymbolTable(this.program.getRepository().getSymbolTable().values()),
                        this.program.getRepository().getHeap().getContent()
                )
        );

        try {
            this.program.oneStepForAllPrograms(programStates);
        } catch (ControllerException e) {
            System.out.println(e.getMessage());
        }

        this.updateWindow();

        programStates = this.program.removeCompletedPrograms(
                this.program.getRepository().getProgramStateList());
        this.program.getRepository().setProgramStateList(programStates);
    }

    public void allSteps() {
        while (!this.program.getRepository().getProgramStateList().isEmpty()) {
            this.oneStep();
        }
    }
}