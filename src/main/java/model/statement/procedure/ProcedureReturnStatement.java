package model.statement.procedure;

import adt.IDictionary;
import exception.StatementException;
import model.ProgramState;
import model.statement.Statement;
import model.type.Type;
import model.value.Value;

import java.util.Stack;

public class ProcedureReturnStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        Stack<IDictionary<String, Value>> symbolTables = state.getSymbolTables();
        symbolTables.pop();

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new ProcedureReturnStatement();
    }

    public String toString() {
        return "return";
    }
}
