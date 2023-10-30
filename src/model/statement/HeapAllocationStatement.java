package model.statement;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import exception.DictionaryException;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.ReferenceType;
import model.value.ReferenceValue;
import model.value.Value;

public class HeapAllocationStatement implements Statement {
    private final String id;
    private final Expression expression;

    public HeapAllocationStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeap<Value> heap = state.getHeap();

        try {
            if (!symbolTable.search(this.id)) {
                throw new StatementException("Variable " + this.id + " is not defined.");
            }
            Value idValue = symbolTable.get(this.id);
            if (!idValue.getType().equals(new ReferenceType(null))) {
                throw new StatementException("Variable " + this.id + " is not a reference type.");
            }

            Value expressionValue = this.expression.eval(symbolTable, heap);
            ReferenceValue referenceValue = (ReferenceValue) idValue;
            if (!expressionValue.getType().equals(referenceValue.getLocationType())) {
                throw new StatementException("Declared type of variable " + this.id +
                        " and type of the assigned expression do not match.");
            }

            int freeAddress = heap.getNextFreeAddress();
            heap.add(expressionValue);
            symbolTable.update(this.id, new ReferenceValue(freeAddress, expressionValue.getType()));
        } catch (ExpressionException | DictionaryException e) {
            throw new StatementException(e.getMessage());
        }

        return state;
    }

    @Override
    public Statement deepCopy() throws StatementException {
        try {
            return new HeapAllocationStatement(this.id, this.expression.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    public String toString() {
        return "new(" + this.id + ", " + this.expression.toString() + ")";
    }
}
