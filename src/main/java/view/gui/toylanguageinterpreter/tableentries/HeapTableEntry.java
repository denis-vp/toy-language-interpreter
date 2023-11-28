package view.gui.toylanguageinterpreter.tableentries;

import javafx.beans.property.SimpleStringProperty;
import model.value.Value;

public class HeapTableEntry {
    private final int address;
    private final Value value;

    public HeapTableEntry(int address, Value value) {
        this.address = address;
        this.value = value;
    }

    public int getAddress() {
        return address;
    }

    public Value getValue() {
        return value;
    }

    public SimpleStringProperty addressProperty() {
        return new SimpleStringProperty(String.valueOf(this.address));
    }

    public SimpleStringProperty valueProperty() {
        return new SimpleStringProperty(this.value.toString());
    }
}
