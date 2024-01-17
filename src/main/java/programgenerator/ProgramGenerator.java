package programgenerator;

import adt.IDictionary;
import adt.MyDictionary;
import exception.StatementException;
import model.expression.*;
import model.statement.*;
import model.statement.assignment.AssignmentStatement;
import model.statement.assignment.CondAssignmentStatement;
import model.statement.barrier.BarrierAwaitStatement;
import model.statement.concurrency.ForkStatement;
import model.statement.concurrency.SleepStatement;
import model.statement.countdownlatch.LatchAwaitStatement;
import model.statement.countdownlatch.LatchCountDownStatement;
import model.statement.countdownlatch.LatchDecStatement;
import model.statement.file.CloseFileReadStatement;
import model.statement.file.FileReadStatement;
import model.statement.file.OpenFileStatement;
import model.statement.heap.HeapAllocationStatement;
import model.statement.heap.HeapWriteStatement;
import model.statement.lock.LockDecStatement;
import model.statement.lock.LockStatement;
import model.statement.lock.UnlockStatement;
import model.statement.loop.ForStatement;
import model.statement.loop.RepeatUntilStatement;
import model.statement.loop.WhileStatement;
import model.statement.selection.IfStatement;
import model.statement.selection.SwitchStatement;
import model.statement.semaphore.SemaphoreAcquireStatement;
import model.statement.semaphore.SemaphoreDecStatement;
import model.statement.semaphore.SemaphoreReleaseStatement;
import model.statement.barrier.BarrierDecStatement;
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
                        ProgramGenerator.getProgram10(),
                        ProgramGenerator.getProgram11(),
                        ProgramGenerator.getProgram12(),
                        ProgramGenerator.getProgram13(),
                        ProgramGenerator.getProgram14(),
                        ProgramGenerator.getProgram15(),
                        ProgramGenerator.getProgram16(),
                        ProgramGenerator.getProgram17()
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

    private static Statement getProgram11() {
//    Conditional assignment example
        Statement declaringA = new VarDecStatement("a", new ReferenceType(new IntType()));
        Statement declaringB = new VarDecStatement("b", new ReferenceType(new IntType()));
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement allocatingA = new HeapAllocationStatement("a", new ValueExpression(new IntValue(0)));
        Statement allocatingB = new HeapAllocationStatement("b", new ValueExpression(new IntValue(0)));
        Statement writingA = new HeapWriteStatement("a", new ValueExpression(new IntValue(1)));
        Statement writingB = new HeapWriteStatement("b", new ValueExpression(new IntValue(2)));
        Statement conditionalAssignment1 = new CondAssignmentStatement(
                "v",
                new RelationalExpression("<", new HeapReadExpression(new VarNameExpression("a")),
                        new HeapReadExpression(new VarNameExpression("b"))),
                new ValueExpression(new IntValue(100)),
                new ValueExpression(new IntValue(200))
        );
        Statement printingV = new PrintStatement(new VarNameExpression("v"));
        Statement conditionalAssignment2 = new CondAssignmentStatement(
                "v",
                new RelationalExpression(">", new ArithmeticExpression("-",
                        new HeapReadExpression(new VarNameExpression("b")),
                        new ValueExpression(new IntValue(2))),
                        new HeapReadExpression(new VarNameExpression("a"))),
                new ValueExpression(new IntValue(100)),
                new ValueExpression(new IntValue(200))
        );

        return ProgramGenerator.buildProgram(declaringA, declaringB, declaringV, allocatingA, allocatingB,
                writingA, writingB, conditionalAssignment1, printingV, conditionalAssignment2, printingV);
    }

    private static Statement getProgram12() {
//    Switch example
        Statement declaringA = new VarDecStatement("a", new IntType());
        Statement declaringB = new VarDecStatement("b", new IntType());
        Statement declaringC = new VarDecStatement("c", new IntType());
        Statement assigningA = new AssignmentStatement("a", new ValueExpression(new IntValue(1)));
        Statement assigningB = new AssignmentStatement("b", new ValueExpression(new IntValue(2)));
        Statement assigningC = new AssignmentStatement("c", new ValueExpression(new IntValue(5)));

        Statement case1 = new CompoundStatement(
                new PrintStatement(new VarNameExpression("a")),
                new PrintStatement(new VarNameExpression("b"))
        );
        Statement case2 = new CompoundStatement(
                new PrintStatement(new ValueExpression(new IntValue(100))),
                new PrintStatement(new ValueExpression(new IntValue(200)))
        );
        Statement case3 = new PrintStatement(new ValueExpression(new IntValue(300)));
        Statement switchStatement = new SwitchStatement(
                new ArithmeticExpression("*", new VarNameExpression("a"), new ValueExpression(new IntValue(10))),
                new ArrayList<>(Arrays.asList(
                        new ArithmeticExpression("*", new VarNameExpression("b"), new VarNameExpression("c")),
                        new ValueExpression(new IntValue(10))
                )),
                new ArrayList<>(Arrays.asList(
                        case1,
                        case2,
                        case3
                ))
        );

        Statement printing300 = new PrintStatement(new ValueExpression(new IntValue(300)));

        return ProgramGenerator.buildProgram(declaringA, declaringB, declaringC, assigningA, assigningB, assigningC,
                switchStatement, printing300);
    }

    private static Statement getProgram13() {
//    Repeat until example
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(0)));

        Statement forkStatement = new ForkStatement(
                new CompoundStatement(
                        new PrintStatement(new VarNameExpression("v")),
                        new AssignmentStatement("v", new ArithmeticExpression("-",
                                new VarNameExpression("v"), new ValueExpression(new IntValue(1))))
                )
        );
        Statement repeatStatement = new RepeatUntilStatement(
                new CompoundStatement(
                        forkStatement,
                        new AssignmentStatement("v", new ArithmeticExpression("+",
                                new VarNameExpression("v"), new ValueExpression(new IntValue(1))))
                ),
                new RelationalExpression("==", new VarNameExpression("v"), new ValueExpression(new IntValue(3)))
        );

        Statement declaringX = new VarDecStatement("x", new IntType());
        Statement assigningX = new AssignmentStatement("x", new ValueExpression(new IntValue(1)));
        Statement declaringY = new VarDecStatement("y", new IntType());
        Statement assigningY = new AssignmentStatement("y", new ValueExpression(new IntValue(2)));
        Statement declaringZ = new VarDecStatement("z", new IntType());
        Statement assigningZ = new AssignmentStatement("z", new ValueExpression(new IntValue(3)));
        Statement declaringW = new VarDecStatement("w", new IntType());
        Statement assigningW = new AssignmentStatement("w", new ValueExpression(new IntValue(4)));
        Statement printingXV10 = new PrintStatement(new ArithmeticExpression("*",
                new VarNameExpression("v"), new ValueExpression(new IntValue(10))));

        return ProgramGenerator.buildProgram(declaringV, assigningV, repeatStatement, declaringX, assigningX,
                declaringY, assigningY, declaringZ, assigningZ, declaringW, assigningW, printingXV10);
    }

    private static Statement getProgram14() {
//    Sleep example
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(10)));
        Statement decrementingV = new AssignmentStatement("v", new UnaryExpression("--", new VarNameExpression("v")));
        Statement forkStatement = new ForkStatement(
                new CompoundStatement(
                        decrementingV,
                        new CompoundStatement(
                                decrementingV,
                                new PrintStatement(new VarNameExpression("v"))
                        )
                )
        );
        Statement sleepStatement = new SleepStatement(10);
        Statement printingV10 = new PrintStatement(new ArithmeticExpression("*",
                new VarNameExpression("v"), new ValueExpression(new IntValue(10))));

        return ProgramGenerator.buildProgram(declaringV, assigningV, forkStatement, sleepStatement, printingV10);
    }

    private static Statement getProgram15() {
//    Count Down Latch example
        Statement declaringV1 = new VarDecStatement("v1", new ReferenceType(new IntType()));
        Statement declaringV2 = new VarDecStatement("v2", new ReferenceType(new IntType()));
        Statement declaringV3 = new VarDecStatement("v3", new ReferenceType(new IntType()));
        Statement declaringCnt = new VarDecStatement("cnt", new IntType());
        Statement allocatingV1 = new HeapAllocationStatement("v1", new ValueExpression(new IntValue(2)));
        Statement allocatingV2 = new HeapAllocationStatement("v2", new ValueExpression(new IntValue(3)));
        Statement allocatingV3 = new HeapAllocationStatement("v3", new ValueExpression(new IntValue(4)));

        Expression readV1 = new HeapReadExpression(new VarNameExpression("v1"));
        Expression readV2 = new HeapReadExpression(new VarNameExpression("v2"));
        Expression readV3 = new HeapReadExpression(new VarNameExpression("v3"));
        Statement printV1 = new PrintStatement(readV1);
        Statement printV2 = new PrintStatement(readV2);
        Statement printV3 = new PrintStatement(readV3);

        Statement newLatch = new LatchDecStatement("cnt", readV2);
        Statement countDown = new LatchCountDownStatement("cnt");

        Statement fork1 = new ForkStatement(
                new CompoundStatement(
                        new HeapWriteStatement("v1", new ArithmeticExpression("*", readV1, new ValueExpression(new IntValue(10)))),
                        new CompoundStatement(
                                printV1,
                                countDown
                        )
                )
        );
        Statement fork2 = new ForkStatement(
                new CompoundStatement(
                        new HeapWriteStatement("v2", new ArithmeticExpression("*", readV2, new ValueExpression(new IntValue(10)))),
                        new CompoundStatement(
                                printV2,
                                countDown
                        )
                )
        );
        Statement fork3 = new ForkStatement(
                new CompoundStatement(
                        new HeapWriteStatement("v3", new ArithmeticExpression("*", readV3, new ValueExpression(new IntValue(10)))),
                        new CompoundStatement(
                                printV3,
                                countDown
                        )
                )
        );

        Statement await = new LatchAwaitStatement("cnt");
        Statement print100 = new PrintStatement(new ValueExpression(new IntValue(100)));

        return ProgramGenerator.buildProgram(
                declaringV1, declaringV2, declaringV3, declaringCnt, allocatingV1, allocatingV2, allocatingV3,
                newLatch, fork1, fork2, fork3, await, print100, countDown, print100
        );
    }

    private static Statement getProgram16() {
//    Semaphore example
        Statement declaringV1 = new VarDecStatement("v1", new ReferenceType(new IntType()));
        Statement declaringCnt = new VarDecStatement("cnt", new IntType());
        Statement allocatingV1 = new HeapAllocationStatement("v1", new ValueExpression(new IntValue(2)));

        Expression readV1 = new HeapReadExpression(new VarNameExpression("v1"));
        Statement acquireSemaphore = new SemaphoreAcquireStatement("cnt");
        Statement releaseSemaphore = new SemaphoreReleaseStatement("cnt");

        Statement newSemaphore = new SemaphoreDecStatement("cnt", readV1);
        Statement fork1 = new ForkStatement(
                new CompoundStatement(
                        acquireSemaphore,
                        new CompoundStatement(
                                new HeapWriteStatement("v1", new ArithmeticExpression("*", readV1, new ValueExpression(new IntValue(10)))),
                                new CompoundStatement(
                                        new PrintStatement(readV1),
                                        releaseSemaphore
                                )
                        )
                )
        );
        Statement fork2 = new ForkStatement(
                new CompoundStatement(
                        acquireSemaphore,
                        new CompoundStatement(
                                new HeapWriteStatement("v1", new ArithmeticExpression("*", readV1, new ValueExpression(new IntValue(10)))),
                                new CompoundStatement(
                                        new HeapWriteStatement("v1", new ArithmeticExpression("*", readV1, new ValueExpression(new IntValue(2)))),
                                        new CompoundStatement(
                                                new PrintStatement(readV1),
                                                releaseSemaphore
                                        )
                                )
                        )
                )
        );
        Statement printing = new PrintStatement(new ArithmeticExpression("-", readV1, new ValueExpression(new IntValue(1))));

        return ProgramGenerator.buildProgram(
                declaringV1, declaringCnt, allocatingV1, newSemaphore, fork1, fork2, acquireSemaphore, printing, releaseSemaphore
        );
    }

    private static Statement getProgram17() {
//    Barrier example
        Statement declaringV1 = new VarDecStatement("v1", new ReferenceType(new IntType()));
        Statement declaringV2 = new VarDecStatement("v2", new ReferenceType(new IntType()));
        Statement declaringV3 = new VarDecStatement("v3", new ReferenceType(new IntType()));
        Statement declaringCnt = new VarDecStatement("cnt", new IntType());
        Statement allocatingV1 = new HeapAllocationStatement("v1", new ValueExpression(new IntValue(2)));
        Statement allocatingV2 = new HeapAllocationStatement("v2", new ValueExpression(new IntValue(3)));
        Statement allocatingV3 = new HeapAllocationStatement("v3", new ValueExpression(new IntValue(4)));

        Expression readV1 = new HeapReadExpression(new VarNameExpression("v1"));
        Expression readV2 = new HeapReadExpression(new VarNameExpression("v2"));
        Expression readV3 = new HeapReadExpression(new VarNameExpression("v3"));
        Statement awaitBarrier = new BarrierAwaitStatement("cnt");

        Statement newBarrier = new BarrierDecStatement("cnt", readV2);
        Statement fork1 = new ForkStatement(
                new CompoundStatement(
                        awaitBarrier,
                        new CompoundStatement(
                                new HeapWriteStatement("v1", new ArithmeticExpression("*", readV1, new ValueExpression(new IntValue(10)))),
                                new PrintStatement(readV1)
                        )
                )
        );
        Statement fork2 = new ForkStatement(
                new CompoundStatement(
                        awaitBarrier,
                        new CompoundStatement(
                                new HeapWriteStatement("v2", new ArithmeticExpression("*", readV2, new ValueExpression(new IntValue(10)))),
                                new CompoundStatement(
                                        new HeapWriteStatement("v2", new ArithmeticExpression("*", readV2, new ValueExpression(new IntValue(10)))),
                                        new PrintStatement(readV2)
                                        )
                        )
                )
        );
        Statement printingV3 = new PrintStatement(readV3);

        return ProgramGenerator.buildProgram(
                declaringV1, declaringV2, declaringV3, declaringCnt, allocatingV1, allocatingV2, allocatingV3,
                newBarrier, fork1, fork2, awaitBarrier, printingV3
        );
    }
}
