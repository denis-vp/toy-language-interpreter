package model.statement;

import model.programstate.ProgramState;

public class NopStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new NopStatement();
    }

    public String toString() {
        return "nop";
    }
}
