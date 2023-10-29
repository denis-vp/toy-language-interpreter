package model.statement;

import exception.StatementException;
import model.programstate.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws StatementException;

    IStatement deepCopy() throws StatementException;
}
