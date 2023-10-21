package model.statement;

import exception.MyException;
import model.programstate.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
}
