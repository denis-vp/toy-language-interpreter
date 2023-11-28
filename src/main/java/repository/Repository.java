package repository;

import adt.IDictionary;
import adt.IHeap;
import exception.ADTException;
import exception.RepositoryException;
import model.ProgramState;
import model.statement.CompoundStatement;
import model.value.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<ProgramState> programStateList = new ArrayList<ProgramState>();
    private final String logFilePath;

    public Repository(ProgramState initialProgram, String logFilePath) {
        this.programStateList.add(initialProgram);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getProgramStateList() {
        return List.copyOf(this.programStateList);
    }

    @Override
    public void setProgramStateList(List<ProgramState> programStateList) {
        this.programStateList.clear();
        this.programStateList.addAll(programStateList);
    }

    @Override
    public IHeap getHeap() {
        return this.programStateList.get(0).getHeap();
    }

    @Override
    public IDictionary<String, Value> getSymbolTable() {
        return this.programStateList.get(0).getSymbolTable();
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws RepositoryException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            logFile.println(programState);
        } catch (IOException e) {
            throw new RepositoryException("Could not open log file!");
        }
    }
}
