package view.gui.toylanguageinterpreter.tableentries;

import javafx.beans.property.SimpleStringProperty;

public class LatchTableEntry {
    private final int address;
    private final int count;

    public LatchTableEntry(int address, int count) {
        this.address = address;
        this.count = count;
    }

    public int getAddress() {
        return this.address;
    }

    public int getCount() {
        return this.count;
    }

    public SimpleStringProperty addressProperty() {
        return new SimpleStringProperty(String.valueOf(this.address));
    }

    public SimpleStringProperty countProperty() {
        return new SimpleStringProperty(String.valueOf(this.count));
    }
}
