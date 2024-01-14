package view.gui.toylanguageinterpreter.tableentries;

import javafx.beans.property.SimpleStringProperty;
import model.value.Value;

public class SymbolTableEntry {
    private final String id;
    private final Value value;

    public SymbolTableEntry(String id, Value value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return this.id;
    }

    public Value getValue() {
        return this.value;
    }

    public SimpleStringProperty idProperty() {
        return new SimpleStringProperty(this.id);
    }

    public SimpleStringProperty valueProperty() {
        return new SimpleStringProperty(this.value.toString());
    }
}
