package model.statement;

import exception.StatementException;
import model.programstate.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState state) throws StatementException;

    Statement deepCopy();
}
