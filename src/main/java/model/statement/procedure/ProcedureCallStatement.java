package model.statement.procedure;

import adt.IDictionary;
import adt.IHeap;
import adt.MyDictionary;
import adt.Pair;
import exception.ExpressionException;
import exception.StatementException;
import model.ProgramState;
import model.expression.Expression;
import model.statement.Statement;
import model.type.Type;
import model.value.Value;

import java.util.List;
import java.util.Stack;

public class ProcedureCallStatement implements Statement {
    private final String name;
    private final List<Expression> parameters;

    public ProcedureCallStatement(String name, List<Expression> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        Stack<IDictionary<String, Value>> symbolTables = state.getSymbolTables();
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
        IDictionary<String, Pair<List<String>, Statement>> procedureTable = state.getProcedureTable();

        if (!procedureTable.search(this.name)) {
            throw new StatementException("Procedure " + this.name + " not defined!");
        }

        Pair<List<String>, Statement> procedure = procedureTable.get(this.name);
        List<String> procedureParameters = procedure.getFirst();
        Statement procedureBody = procedure.getSecond();

        if (procedureParameters.size() != this.parameters.size()) {
            throw new StatementException("Procedure " + this.name + " expects " + procedureParameters.size() + " parameters, but got " + this.parameters.size() + "!");
        }

        IDictionary<String, Value> newSymbolTable = new MyDictionary<>();
        try {
            for (int i = 0; i < procedureParameters.size(); i++) {
                String parameter = procedureParameters.get(i);
                Expression argument = this.parameters.get(i);
                newSymbolTable.add(parameter, argument.eval(symbolTable, heap));
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        symbolTables.push(newSymbolTable);

        state.getExecutionStack().push(new ProcedureReturnStatement());
        state.getExecutionStack().push(procedureBody);

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        List<Expression> newParameters = this.parameters.stream().toList();
        return new ProcedureCallStatement(this.name, newParameters);
    }

    public String toString() {
        return "call " + this.name + "(" + this.parameters + ")";
    }
}
