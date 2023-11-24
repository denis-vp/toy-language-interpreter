package model.expression;

import adt.IDictionary;
import adt.IHeap;
import adt.MyConcurrentDictionary;
import adt.MyHeap;
import model.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticExpressionTest {
    IDictionary<String, Value> symbolTable;
    IHeap heap;

    @BeforeEach
    void setUp() {
        this.symbolTable = new MyConcurrentDictionary<>();
        this.heap = new MyHeap();
    }

    @Test
    void eval() {
        ValueExpression valueExpression = new ValueExpression(new IntValue(1));
        assertEquals(new IntValue(1), valueExpression.eval(symbolTable, heap));

        ValueExpression valueExpression2 = new ValueExpression(new BoolValue(true));
        assertEquals(new BoolValue(true), valueExpression2.eval(symbolTable, heap));

        ValueExpression valueExpression3 = new ValueExpression(new StringValue("test"));
        assertEquals(new StringValue("test"), valueExpression3.eval(symbolTable, heap));

        ValueExpression valueExpression4 = new ValueExpression(new ReferenceValue(1, new IntValue(1).getType()));
        assertEquals(new ReferenceValue(1, new IntValue(1).getType()), valueExpression4.eval(symbolTable, heap));

    }
}