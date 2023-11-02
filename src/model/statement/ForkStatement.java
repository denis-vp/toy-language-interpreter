package model.statement;

import adt.IDictionary;
import adt.IStack;
import adt.MyStack;
import exception.StatementException;
import model.ProgramState;
import model.type.Type;
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
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        this.statement.typeCheck(typeEnvironment.deepCopy());
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    public String toString() {
        return "fork(" + this.statement + ")";
    }
}
