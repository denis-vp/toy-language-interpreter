package model.statement;

import exception.DictionaryException;
import exception.ExpressionException;
import exception.StackException;
import exception.StatementException;
import model.programstate.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws StatementException, DictionaryException, ExpressionException, StackException;

    IStatement deepCopy() throws ExpressionException;
}
