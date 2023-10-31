package model.statement;

import datastructure.MyIDictionary;
import datastructure.MyIStack;
import datastructure.MyStack;
import exception.ProgramStateException;
import exception.StatementException;
import model.programstate.ProgramState;
import model.value.Value;

public class ForkStatement implements Statement {
    private final Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        try {
            MyIStack<Statement> newStack = new MyStack<>();
            MyIDictionary<String, Value> newSymbolTable = state.getSymbolTable().deepCopy();

            return new ProgramState(this.statement, newStack, newSymbolTable, state.getOutput(),
                    state.getHeap(), state.getFileTable());

        } catch (ProgramStateException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() throws StatementException {
        return new ForkStatement(this.statement.deepCopy());
    }

    public String toString() {
        return "fork(" + this.statement + ")";
    }
}
