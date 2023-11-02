package model.statement;

import adt.IDictionary;
import model.ProgramState;
import model.type.Type;

public class NopStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) {
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new NopStatement();
    }

    public String toString() {
        return "nop";
    }
}
