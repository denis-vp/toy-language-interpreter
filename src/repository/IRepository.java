package repository;

import model.programstate.ProgramState;

import java.util.List;

public interface IRepository {
    void add(ProgramState programState);

    ProgramState get(int index);

    void set(int index, ProgramState programState);

    int size();

    List<ProgramState> getProgramStates();

    void logProgramStateExecution();

    ProgramState getCurrentProgram();
}
