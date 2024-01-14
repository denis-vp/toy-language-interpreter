package view.gui.toylanguageinterpreter.tableentries;

import javafx.beans.property.SimpleStringProperty;

public class LockTableEntry {
    private final int address;
    private final int threadId;

    public LockTableEntry(int address, int threadId) {
        this.address = address;
        this.threadId = threadId;
    }

    public int getAddress() {
        return this.address;
    }

    public int getThreadId() {
        return this.threadId;
    }

    public SimpleStringProperty addressProperty() {
        return new SimpleStringProperty(String.valueOf(this.address));
    }

    public SimpleStringProperty threadIdProperty() {
        return new SimpleStringProperty(String.valueOf(this.threadId));
    }
}
