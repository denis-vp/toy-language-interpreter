package view.gui.toylanguageinterpreter.tableentries;

import adt.Pair;
import adt.Triplet;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class SemaphoreTableEntry {
    private final int address;
    private final Pair<Integer, List<Integer>> value;

    public SemaphoreTableEntry(int address, Pair<Integer, List<Integer>> value) {
        this.address = address;
        this.value = value;
    }

    public int getAddress() {
        return this.address;
    }

    public Pair<Integer, List<Integer>> getValue() {
        return this.value;
    }

    public SimpleStringProperty addressProperty() {
        return new SimpleStringProperty(String.valueOf(this.address));
    }

    public SimpleStringProperty valueProperty() {
        return new SimpleStringProperty(String.valueOf(this.value));
    }
}
