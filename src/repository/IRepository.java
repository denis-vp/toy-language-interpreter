package repository;

import exception.RepositoryException;
import model.programstate.ProgramState;

import java.util.List;

public interface IRepository {

    List<ProgramState> getProgramStateList();

    void setProgramStateList(List<ProgramState> programStateList);

    void logProgramStateExecution(ProgramState programState) throws RepositoryException;
}
