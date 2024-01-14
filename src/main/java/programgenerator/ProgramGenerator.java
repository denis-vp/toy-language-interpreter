package programgenerator;

import adt.IDictionary;
import adt.MyDictionary;
import exception.StatementException;
import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramGenerator {
    public static List<Statement> getPrograms() {
        ArrayList<Statement> programs = new ArrayList<>(
                Arrays.asList(
                        ProgramGenerator.getProgram1(),
                        ProgramGenerator.getProgram2(),
                        ProgramGenerator.getProgram3(),
                        ProgramGenerator.getProgram4(),
                        ProgramGenerator.getProgram5(),
                        ProgramGenerator.getProgram6(),
                        ProgramGenerator.getProgram7(),
                        ProgramGenerator.getProgram8(),
                        ProgramGenerator.getProgram9(),
                        ProgramGenerator.getProgram10()
                ));

        for (int i = 0; i < programs.size(); i++) {
            try {
                IDictionary<String, Type> typeEnvironment = new MyDictionary<>();
                programs.get(i).typeCheck(typeEnvironment);
            } catch (StatementException e) {
                System.out.println(e.getMessage());
                programs.remove(i);
                i--;
            }
        }

        return programs;
    }

    private static Statement buildProgram(Statement... statements) {
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

    private static Statement getProgram1() {
//    Basic Example
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));

        return ProgramGenerator.buildProgram(declaringV, assigningV, printingV);
    }

    private static Statement getProgram2() {
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

        return ProgramGenerator.buildProgram(declaringA, declaringB, assigningA, assigningB, printingB);
    }

    private static Statement getProgram3() {
//    If statement example
        Statement declaringA = new VarDecStatement("a", new BoolType());
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningA = new AssignmentStatement("a", new ValueExpression(new BoolValue(true)));
        Statement ifStatement = new IfStatement(new VarNameExpression("a"),
                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                new AssignmentStatement("v", new ValueExpression(new IntValue(3))));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));

        return ProgramGenerator.buildProgram(declaringA, declaringV, assigningA, ifStatement, printingV);
    }

    private static Statement getProgram4() {
//    File handling example
        Statement declaringV = new VarDecStatement("v", new StringType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new StringValue("./input/test.in")));
        Statement openingFile = new OpenFileStatement(new VarNameExpression("v"));
        Statement declaringC = new VarDecStatement("c", new IntType());
        Statement readingC = new FileReadStatement(new VarNameExpression("v"), "c");
        Statement printingC = new PrintStatement(new VarNameExpression("c"));
        Statement closingFile = new CloseFileReadStatement(new VarNameExpression("v"));

        return ProgramGenerator.buildProgram(declaringV, assigningV, openingFile, declaringC, readingC,
                printingC, readingC, printingC, closingFile);
    }

    private static Statement getProgram5() {
//    Relational expressions example
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(1)));
        Statement ifStatement = new IfStatement(new RelationalExpression("<", new VarNameExpression("v"), new ValueExpression(new IntValue(3))),
                new PrintStatement(new VarNameExpression("v")),
                new NopStatement());

        return ProgramGenerator.buildProgram(declaringV, assigningV, ifStatement);
    }

    private static Statement getProgram6() {
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

        return ProgramGenerator.buildProgram(declaringV, allocatingV, declaringA, allocatingA,
                allocatingV2, printingA, declaringG, allocatingG, allocatingG2, printingG);
    }

    private static Statement getProgram7() {
//    While statement example
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(4)));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));
        Statement decrementingV = new AssignmentStatement("v", new ArithmeticExpression("-",
                new VarNameExpression("v"), new ValueExpression(new IntValue(1))));
        Statement whileStatement = new WhileStatement(new RelationalExpression(">",
                new VarNameExpression("v"), new ValueExpression(new IntValue(0))),
                new CompoundStatement(printingV, decrementingV));

        return ProgramGenerator.buildProgram(declaringV, assigningV, whileStatement);
    }

    private static Statement getProgram8() {
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

        return ProgramGenerator.buildProgram(declaringV, assigningV, forStatement, printingV);
    }

    private static Statement getProgram9() {
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

        return ProgramGenerator.buildProgram(declaringV, declaringA, assigningV, allocatingA, thread1, printingV, printingA);
    }

    private static Statement getProgram10() {
//    Lock example
        Statement declaringV1 = new VarDecStatement("v1", new ReferenceType(new IntType()));
        Statement declaringV2 = new VarDecStatement("v2", new ReferenceType(new IntType()));
        Statement declaringX = new VarDecStatement("x", new IntType());
        Statement declaringQ = new VarDecStatement("q", new IntType());
        Statement allocatingV1 = new HeapAllocationStatement("v1", new ValueExpression(new IntValue(20)));
        Statement allocatingV2 = new HeapAllocationStatement("v2", new ValueExpression(new IntValue(30)));

        Statement newLockX = new LockDecStatement("x");
        Statement lockX = new LockStatement("x");
        Statement unlockX = new UnlockStatement("x");
        Expression readHeapV1 = new HeapReadExpression(new VarNameExpression("v1"));
        Statement fork1 = new ForkStatement(
                new CompoundStatement(
                        new ForkStatement(
                                new CompoundStatement(
                                        lockX,
                                        new CompoundStatement(
                                                new HeapWriteStatement("v1", new ArithmeticExpression("-",
                                                        readHeapV1, new ValueExpression(new IntValue(1)))),
                                                unlockX
                                        )
                                )
                        ),
                        new CompoundStatement(
                                lockX,
                                new CompoundStatement(
                                        new HeapWriteStatement("v1", new ArithmeticExpression("*",
                                                readHeapV1, new ValueExpression(new IntValue(10)))),
                                        unlockX
                                )
                        )
                )
        );

        Statement newLockQ = new LockDecStatement("q");
        Statement lockQ = new LockStatement("q");
        Statement unlockQ = new UnlockStatement("q");
        Expression readHeapV2 = new HeapReadExpression(new VarNameExpression("v2"));
        Statement fork2 = new ForkStatement(
                new CompoundStatement(
                        new ForkStatement(
                                new CompoundStatement(
                                        lockQ,
                                        new CompoundStatement(
                                                new HeapWriteStatement("v2", new ArithmeticExpression("+",
                                                        readHeapV2, new ValueExpression(new IntValue(5)))),
                                                unlockQ
                                        )
                                )
                        ),
                        new CompoundStatement(
                                lockQ,
                                new CompoundStatement(
                                        new HeapWriteStatement("v2", new ArithmeticExpression("*",
                                                readHeapV2, new ValueExpression(new IntValue(10)))),
                                        unlockQ
                                )
                        )
                )
        );
        Statement nop = new NopStatement();
        Statement printV1 = new PrintStatement(readHeapV1);
        Statement printV2 = new PrintStatement(readHeapV2);

        return ProgramGenerator.buildProgram(
                declaringV1, declaringV2, declaringX, declaringQ, allocatingV1, allocatingV2,
                newLockX, fork1, newLockQ, fork2,
                nop, nop, nop, nop,
                lockX, printV1, unlockX,
                lockQ, printV2, unlockQ
        );
    }
}
