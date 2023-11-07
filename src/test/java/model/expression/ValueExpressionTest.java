package model.expression;

import adt.IDictionary;
import adt.IHeap;
import adt.MyDictionary;
import adt.MyHeap;
import model.value.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueExpressionTest {
    @Test
    void eval() {
        IDictionary<String, Value> symbolTable = new MyDictionary<>();
        IHeap heap = new MyHeap();

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