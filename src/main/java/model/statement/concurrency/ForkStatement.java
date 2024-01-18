package model.statement.concurrency;

import adt.IDictionary;
import adt.IStack;
import adt.MyStack;
import exception.StatementException;
import model.ProgramState;
import model.statement.Statement;
import model.type.Type;
import model.value.Value;

import java.util.Stack;

public class ForkStatement implements Statement {
    private final Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<Statement> newExecutionStack = new MyStack<>();
        Stack<IDictionary<String, Value>> newSymbolTables = new Stack<>();
        for (IDictionary<String, Value> symbolTable : state.getSymbolTables()) {
            newSymbolTables.push(symbolTable.deepCopy());
        }

        return new ProgramState(this.statement, newExecutionStack, newSymbolTables,
                state.getHeap(), state.getFileTable(), state.getOutput(), state.getLockTable(), state.getLatchTable(),
                state.getSemaphoreTable(), state.getBarrierTable(), state.getProcedureTable());
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
