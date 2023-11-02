package repository;

import adt.IDictionary;
import adt.IHeap;
import exception.RepositoryException;
import model.ProgramState;
import model.value.Value;

import java.util.List;

public interface IRepository {

    List<ProgramState> getProgramStateList();

    void setProgramStateList(List<ProgramState> programStateList);

    IHeap getHeap();

    IDictionary<String, Value> getSymbolTable();

    void logProgramStateExecution(ProgramState programState) throws RepositoryException;
}
