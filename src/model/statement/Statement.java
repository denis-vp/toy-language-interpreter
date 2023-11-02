package model.statement;

import adt.IDictionary;
import exception.StatementException;
import model.ProgramState;
import model.type.Type;

public interface Statement {
    ProgramState execute(ProgramState state) throws StatementException;

    IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException;

    Statement deepCopy();
}
