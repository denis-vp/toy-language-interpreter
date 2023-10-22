package repository;

import model.programstate.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<ProgramState> programStates = new ArrayList<ProgramState>();
    private final int currentProgramIndex = 0;

    public void add(ProgramState programState) {
        this.programStates.add(programState);
    }

    public ProgramState get(int index) {
        return this.programStates.get(index);
    }

    public void set(int index, ProgramState programState) {
        this.programStates.set(index, programState);
    }

    public int size() {
        return this.programStates.size();
    }

    public List<ProgramState> getProgramStates() {
        return List.copyOf(this.programStates);
    }

    public void logProgramStateExecution() {
        System.out.println(this.programStates.get(this.currentProgramIndex));
    }

    @Override
    public ProgramState getCurrentProgram() {
        return this.programStates.get(this.currentProgramIndex);
    }
}
