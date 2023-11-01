package repository;

import exception.RepositoryException;
import model.programstate.ProgramState;

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
    public void logProgramStateExecution(ProgramState programState) throws RepositoryException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            logFile.println(programState);
        } catch (IOException e) {
            throw new RepositoryException("Could not open log file!");
        }
    }
}
