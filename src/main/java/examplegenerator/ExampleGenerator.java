package examplegenerator;

import adt.IDictionary;
import adt.MyDictionary;
import exception.StatementException;
import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

import java.util.Arrays;
import java.util.List;

public class ExampleGenerator {
    public static List<Statement> getExamples() {
        List<Statement> examples = Arrays.asList(
                ExampleGenerator.getExample1(),
                ExampleGenerator.getExample2(),
                ExampleGenerator.getExample3(),
                ExampleGenerator.getExample4(),
                ExampleGenerator.getExample5(),
                ExampleGenerator.getExample6(),
                ExampleGenerator.getExample7(),
                ExampleGenerator.getExample8(),
                ExampleGenerator.getExample9()
        );

        for (Statement example : examples) {
            try {
                IDictionary<String, Type> typeEnvironment = new MyDictionary<>();
                example.typeCheck(typeEnvironment);
            } catch (StatementException e) {
                System.out.println(e.getMessage());
                examples.remove(example);
            }
        }

        return examples;
    }

    private static Statement buildExample(Statement... statements) {
        if (statements.length == 0) {
            return new NopStatement();
        } else if (statements.length == 1) {
            return statements[0];
        }

        Statement example = new CompoundStatement(statements[0], statements[1]);
        for (int i = 2; i < statements.length; i++) {
            example = new CompoundStatement(example, statements[i]);
        }

        return example;
    }

    private static Statement getExample1() {
//    Basic Example
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));

        return ExampleGenerator.buildExample(declaringV, assigningV, printingV);
    }

    private static Statement getExample2() {
//    Arithmetic expressions example
        Statement declaringA = new VarDecStatement("a", new IntType());
        Statement declaringB = new VarDecStatement("b", new IntType());
        Statement assigningA = new AssignmentStatement("a",
                new ArithmeticExpression("+", new ValueExpression(new IntValue(2)),
                        new ArithmeticExpression("*", new ValueExpression(new IntValue(3)),
                                new ValueExpression(new IntValue(5)))));
        Statement assigningB = new AssignmentStatement("b",
                new ArithmeticExpression("+", new VarNameExpression("a"),
                        new ValueExpression(new IntValue(1))));
        Statement printingB = new PrintStatement(new VarNameExpression("b"));

        return ExampleGenerator.buildExample(declaringA, declaringB, assigningA, assigningB, printingB);
    }

    private static Statement getExample3() {
//    If statement example
        Statement declaringA = new VarDecStatement("a", new BoolType());
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningA = new AssignmentStatement("a", new ValueExpression(new BoolValue(true)));
        Statement ifStatement = new IfStatement(new VarNameExpression("a"),
                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                new AssignmentStatement("v", new ValueExpression(new IntValue(3))));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));

        return ExampleGenerator.buildExample(declaringA, declaringV, assigningA, ifStatement, printingV);
    }

    private static Statement getExample4() {
//    File handling example
        Statement declaringV = new VarDecStatement("v", new StringType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new StringValue("./input/test.in")));
        Statement openingFile = new OpenFileStatement(new VarNameExpression("v"));
        Statement declaringC = new VarDecStatement("c", new IntType());
        Statement readingC = new FileReadStatement(new VarNameExpression("v"), "c");
        Statement printingC = new PrintStatement(new VarNameExpression("c"));
        Statement closingFile = new CloseFileReadStatement(new VarNameExpression("v"));

        return ExampleGenerator.buildExample(declaringV, assigningV, openingFile, declaringC, readingC,
                printingC, readingC, printingC, closingFile);
    }

    private static Statement getExample5() {
//    Relational expressions example
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(1)));
        Statement ifStatement = new IfStatement(new RelationalExpression("<", new VarNameExpression("v"), new ValueExpression(new IntValue(3))),
                new PrintStatement(new VarNameExpression("v")),
                new NopStatement());

        return ExampleGenerator.buildExample(declaringV, assigningV, ifStatement);
    }

    private static Statement getExample6() {
//    Heap handling example
        Statement declaringV = new VarDecStatement("v", new ReferenceType(new IntType()));
        Statement allocatingV = new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)));
        Statement declaringA = new VarDecStatement("a", new ReferenceType(new ReferenceType(new IntType())));
        Statement allocatingA = new HeapAllocationStatement("a", new VarNameExpression("v"));
        Statement allocatingV2 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(30)));
        Statement printingA = new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VarNameExpression("a"))));
        Statement declaringG = new VarDecStatement("g", new ReferenceType(new IntType()));
        Statement allocatingG = new HeapAllocationStatement("g", new ValueExpression(new IntValue(5)));
        Statement allocatingG2 = new HeapAllocationStatement("g", new ValueExpression(new IntValue(10)));
        Statement printingG = new PrintStatement(new HeapReadExpression(new VarNameExpression("g")));

        return ExampleGenerator.buildExample(declaringV, allocatingV, declaringA, allocatingA,
                allocatingV2, printingA, declaringG, allocatingG, allocatingG2, printingG);
    }

    private static Statement getExample7() {
//    While statement example
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(4)));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));
        Statement decrementingV = new AssignmentStatement("v", new ArithmeticExpression("-",
                new VarNameExpression("v"), new ValueExpression(new IntValue(1))));
        Statement whileStatement = new WhileStatement(new RelationalExpression(">",
                new VarNameExpression("v"), new ValueExpression(new IntValue(0))),
                new CompoundStatement(printingV, decrementingV));

        return ExampleGenerator.buildExample(declaringV, assigningV, whileStatement);
    }

    private static Statement getExample8() {
//    For statement example
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

        return ExampleGenerator.buildExample(declaringV, assigningV, forStatement, printingV);
    }

    private static Statement getExample9() {
//    Threading example
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement declaringA = new VarDecStatement("a", new ReferenceType(new IntType()));
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(10)));
        Statement allocatingA = new HeapAllocationStatement("a", new ValueExpression(new IntValue(22)));
        Statement writingA = new HeapWriteStatement("a", new ValueExpression(new IntValue(30)));
        Statement assigningV2 = new AssignmentStatement("v", new ValueExpression(new IntValue(32)));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));
        Statement printingA = new PrintStatement(new HeapReadExpression(new VarNameExpression("a")));
        Statement thread1 = new ForkStatement(new CompoundStatement(writingA,
                new CompoundStatement(assigningV2, new CompoundStatement(printingV, printingA))));

        return ExampleGenerator.buildExample(declaringV, declaringA, assigningV, allocatingA, thread1, printingV, printingA);
    }
}
