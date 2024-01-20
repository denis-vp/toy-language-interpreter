package view.gui.toylanguageinterpreter.tableentries;

import adt.Pair;
import javafx.beans.property.SimpleStringProperty;
import model.statement.Statement;

import java.util.List;

public class ProcedureTableEntry {
    private final String name;
    private final Pair<List<String>, Statement> signature;

    public ProcedureTableEntry(String name, Pair<List<String>, Statement> signature) {
        this.name = name;
        this.signature = signature;
    }

    public String getName() {
        return this.name;
    }

    public Pair<List<String>, Statement> getSignature() {
        return this.signature;
    }

    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(String.valueOf(this.name));
    }

    public SimpleStringProperty signatureProperty() {
        return new SimpleStringProperty(String.valueOf(this.signature));
    }
}
