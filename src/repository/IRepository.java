package repository;

import exception.RepositoryException;
import model.programstate.ProgramState;

import java.util.List;

public interface IRepository {
    void add(ProgramState programState);

    ProgramState get(int index);

    void set(int index, ProgramState programState);

    int size();

    boolean isEmpty();

    List<ProgramState> getProgramStates();

    void setProgramStates(List<ProgramState> programStates);

    void logProgramStateExecution(ProgramState programState) throws RepositoryException;
}
