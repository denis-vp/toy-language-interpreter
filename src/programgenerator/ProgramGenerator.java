package programgenerator;

import model.expression.*;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.ReferenceType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

public class ProgramGenerator {
//    Basic Example
    public static Statement getExample1() {
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));

        Statement statement = new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        printingV));

        return statement;
    }

//    Arithmetic expressions example
    public static Statement getExample2() {
        Statement declaringA = new VarDecStatement("a", new IntType());
        Statement declaringB = new VarDecStatement("b", new IntType());
        Statement assigningA = new AssignmentStatement("a",
                new ArithmeticExpression("+" , new ValueExpression(new IntValue(2)),
                        new ArithmeticExpression("*", new ValueExpression(new IntValue(3)),
                                new ValueExpression(new IntValue(5)))));
        Statement assigningB = new AssignmentStatement("b",
                new ArithmeticExpression("+", new VarNameExpression("a"),
                        new ValueExpression(new IntValue(1))));
        Statement printingB = new PrintStatement(new VarNameExpression("b"));

        Statement statement = new CompoundStatement(declaringA,
                new CompoundStatement(declaringB,
                        new CompoundStatement(assigningA,
                                new CompoundStatement(assigningB,
                                        printingB))));

        return statement;
    }

//    If statement example
    public static Statement getExample3() {
        Statement declaringA = new VarDecStatement("a", new BoolType());
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningA = new AssignmentStatement("a", new ValueExpression(new BoolValue(true)));
        Statement ifStatement = new IfStatement(new VarNameExpression("a"),
                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                new AssignmentStatement("v", new ValueExpression(new IntValue(3))));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));

        Statement statement = new CompoundStatement(declaringA,
                new CompoundStatement(declaringV,
                        new CompoundStatement(assigningA,
                                new CompoundStatement(ifStatement,
                                        printingV))));

        return statement;
    }

//    File handling example
    public static Statement getExample4() {
        Statement declaringV = new VarDecStatement("v", new StringType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new StringValue("./input/test.in")));
        Statement openingFile = new OpenFileReadStatement(new VarNameExpression("v"));
        Statement declaringC = new VarDecStatement("c", new IntType());
        Statement readingC = new FileReadStatement(new VarNameExpression("v"), "c");
        Statement printingC = new PrintStatement(new VarNameExpression("c"));
        Statement closingFile = new CloseFileReadStatement(new VarNameExpression("v"));

        Statement statement = new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        new CompoundStatement(openingFile,
                                new CompoundStatement(declaringC,
                                        new CompoundStatement(readingC,
                                                new CompoundStatement(printingC,
                                                        new CompoundStatement(readingC,
                                                                new CompoundStatement(printingC,
                                                                        closingFile))))))));

        return statement;
    }

//    Relational expressions example
    public static Statement getExample5() {
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(1)));
        Statement ifStatement = new IfStatement(new RelationalExpression("<", new VarNameExpression("v"), new ValueExpression(new IntValue(3))),
                new PrintStatement(new VarNameExpression("v")),
                new NopStatement());

        Statement statement = new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        ifStatement));

        return statement;
    }

//    Heap handling example
    public static Statement getExample6() {
        Statement declaringV = new VarDecStatement("v", new ReferenceType(new IntType()));
        Statement allocatingV = new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)));
        Statement printingV = new PrintStatement(new HeapReadExpression(new VarNameExpression("v")));
        Statement writingV = new HeapWriteStatement("v", new ValueExpression(new IntValue(30)));
        Statement printingV2 = new PrintStatement(new ArithmeticExpression("+",
                new HeapReadExpression(new VarNameExpression("v")), new ValueExpression(new IntValue(5))));

        Statement statement = new CompoundStatement(declaringV,
                new CompoundStatement(allocatingV,
                        new CompoundStatement(printingV,
                                new CompoundStatement(writingV,
                                        printingV2))));

        return statement;
    }

//    While statement example
    public static Statement getExample7() {
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(4)));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));
        Statement decrementingV = new AssignmentStatement("v", new ArithmeticExpression("-",
                new VarNameExpression("v"), new ValueExpression(new IntValue(1))));
        Statement whileStatement = new WhileStatement(new RelationalExpression(">",
                new VarNameExpression("v"), new ValueExpression(new IntValue(0))),
                new CompoundStatement(printingV, decrementingV));

        Statement statement = new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        whileStatement));

        return statement;
    }

//    For statement example
    public static Statement getExample8() {
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(1)));
        Statement declaringAssigningI = new CompoundStatement(new VarDecStatement("i", new IntType()),
                new AssignmentStatement("i", new ValueExpression(new IntValue(2))));
        Statement incrementingI = new AssignmentStatement("i", new ArithmeticExpression("+",
                new VarNameExpression("i"), new ValueExpression(new IntValue(1))));
        Statement multiplyingV = new AssignmentStatement("v", new ArithmeticExpression("*",
                new VarNameExpression("v"), new VarNameExpression("i")));
        Statement forStatement = new ForStatement(declaringAssigningI, new RelationalExpression("<",
                new VarNameExpression("i"), new ValueExpression(new IntValue(10))), incrementingI, multiplyingV);
        Statement printingV = new PrintStatement(new VarNameExpression("v"));

        Statement statement = new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        new CompoundStatement(forStatement,
                                printingV)));

        return statement;
    }
}
