package model.statement;

import adt.IDictionary;
import adt.IStack;
import adt.MyStack;
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
        IStack<Statement> newExecutionStack = new MyStack<>();
        IDictionary<String, Value> newSymbolTable = state.getSymbolTable().deepCopy();

        return new ProgramState(this.statement, newExecutionStack, newSymbolTable,
                state.getHeap(), state.getFileTable(), state.getOutput()
        );
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    public String toString() {
        return "fork(" + this.statement + ")";
    }
}
