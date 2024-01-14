package model.statement;

import adt.IDictionary;
import exception.StatementException;
import model.ProgramState;
import model.type.Type;
import model.value.Value;


public class VarDecStatement implements Statement {
    private final String id;
    private final Type type;

    public VarDecStatement(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();

        if (symbolTable.search(this.id)) {
            throw new StatementException("Variable " + this.id + " is already defined.");
        } else {
            symbolTable.add(this.id, this.type.defaultValue());
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        typeEnvironment.add(this.id, this.type);
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new VarDecStatement(this.id, this.type.deepCopy());
    }

    public String toString() {
        return this.type + " " + this.id;
    }
}
