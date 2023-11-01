package examplegenerator;

import adt.MyDictionary;
import adt.MyHeap;
import adt.MyList;
import adt.MyStack;
import controller.Controller;
import model.expression.*;
import model.programstate.ProgramState;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.ReferenceType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.cli.commands.Command;
import view.cli.commands.RunExample;

import java.util.ArrayList;
import java.util.List;

public class ExampleGenerator {
    public static List<List<Command>> getExampleCommands(String logFilePath) {
        List<List<Command>> commandList = new ArrayList<>();

        ProgramState ex1 = new ProgramState(ExampleGenerator.getExample1(), new MyStack<>(), new MyDictionary<>(),
                new MyHeap(), new MyDictionary<>(), new MyList<>());
        IRepository repository1 = new Repository(ex1, logFilePath);
        Controller controller1 = new Controller(repository1);
        List<Command> commands1 = new ArrayList<>();
        commands1.add(new RunExample("1", "run example 1", controller1));
        commandList.add(commands1);

        ProgramState ex2 = new ProgramState(ExampleGenerator.getExample2(), new MyStack<>(), new MyDictionary<>(),
                new MyHeap(), new MyDictionary<>(), new MyList<>());
        IRepository repository2 = new Repository(ex2, logFilePath);
        Controller controller2 = new Controller(repository2);
        List<Command> commands2 = new ArrayList<>();
        commands2.add(new RunExample("2", "run example 2", controller2));
        commandList.add(commands2);

        ProgramState ex3 = new ProgramState(ExampleGenerator.getExample3(), new MyStack<>(), new MyDictionary<>(),
                new MyHeap(), new MyDictionary<>(), new MyList<>());
        IRepository repository3 = new Repository(ex3, logFilePath);
        Controller controller3 = new Controller(repository3);
        List<Command> commands3 = new ArrayList<>();
        commands3.add(new RunExample("3", "run example 3", controller3));
        commandList.add(commands3);

        ProgramState ex4 = new ProgramState(ExampleGenerator.getExample4(), new MyStack<>(), new MyDictionary<>(),
                new MyHeap(), new MyDictionary<>(), new MyList<>());
        IRepository repository4 = new Repository(ex4, logFilePath);
        Controller controller4 = new Controller(repository4);
        List<Command> commands4 = new ArrayList<>();
        commands4.add(new RunExample("4", "run example 4", controller4));
        commandList.add(commands4);

        ProgramState ex5 = new ProgramState(ExampleGenerator.getExample5(), new MyStack<>(), new MyDictionary<>(),
                new MyHeap(), new MyDictionary<>(), new MyList<>());
        IRepository repository5 = new Repository(ex5, logFilePath);
        Controller controller5 = new Controller(repository5);
        List<Command> commands5 = new ArrayList<>();
        commands5.add(new RunExample("5", "run example 5", controller5));
        commandList.add(commands5);

        ProgramState ex6 = new ProgramState(ExampleGenerator.getExample6(), new MyStack<>(), new MyDictionary<>(),
                new MyHeap(), new MyDictionary<>(), new MyList<>());
        IRepository repository6 = new Repository(ex6, logFilePath);
        Controller controller6 = new Controller(repository6);
        List<Command> commands6 = new ArrayList<>();
        commands6.add(new RunExample("6", "run example 6", controller6));
        commandList.add(commands6);

        ProgramState ex7 = new ProgramState(ExampleGenerator.getExample7(), new MyStack<>(), new MyDictionary<>(),
                new MyHeap(), new MyDictionary<>(), new MyList<>());
        IRepository repository7 = new Repository(ex7, logFilePath);
        Controller controller7 = new Controller(repository7);
        List<Command> commands7 = new ArrayList<>();
        commands7.add(new RunExample("7", "run example 7", controller7));
        commandList.add(commands7);

        ProgramState ex8 = new ProgramState(ExampleGenerator.getExample8(), new MyStack<>(), new MyDictionary<>(),
                new MyHeap(), new MyDictionary<>(), new MyList<>());
        IRepository repository8 = new Repository(ex8, logFilePath);
        Controller controller8 = new Controller(repository8);
        List<Command> commands8 = new ArrayList<>();
        commands8.add(new RunExample("8", "run example 8", controller8));
        commandList.add(commands8);

        ProgramState ex9 = new ProgramState(ExampleGenerator.getExample9(), new MyStack<>(), new MyDictionary<>(),
                new MyHeap(), new MyDictionary<>(), new MyList<>());
        IRepository repository9 = new Repository(ex9, logFilePath);
        Controller controller9 = new Controller(repository9);
        List<Command> commands9 = new ArrayList<>();
        commands9.add(new RunExample("9", "run example 9", controller9));
        commandList.add(commands9);

        return commandList;
    }

    private static Statement getExample1() {
//    Basic Example
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));

        return new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        printingV));
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

        return new CompoundStatement(declaringA,
                new CompoundStatement(declaringB,
                        new CompoundStatement(assigningA,
                                new CompoundStatement(assigningB,
                                        printingB))));
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

        return new CompoundStatement(declaringA,
                new CompoundStatement(declaringV,
                        new CompoundStatement(assigningA,
                                new CompoundStatement(ifStatement,
                                        printingV))));
    }

    private static Statement getExample4() {
//    File handling example
        Statement declaringV = new VarDecStatement("v", new StringType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new StringValue("./input/test.in")));
        Statement openingFile = new OpenFileReadStatement(new VarNameExpression("v"));
        Statement declaringC = new VarDecStatement("c", new IntType());
        Statement readingC = new FileReadStatement(new VarNameExpression("v"), "c");
        Statement printingC = new PrintStatement(new VarNameExpression("c"));
        Statement closingFile = new CloseFileReadStatement(new VarNameExpression("v"));

        return new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        new CompoundStatement(openingFile,
                                new CompoundStatement(declaringC,
                                        new CompoundStatement(readingC,
                                                new CompoundStatement(printingC,
                                                        new CompoundStatement(readingC,
                                                                new CompoundStatement(printingC,
                                                                        closingFile))))))));
    }

    private static Statement getExample5() {
//    Relational expressions example
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(1)));
        Statement ifStatement = new IfStatement(new RelationalExpression("<", new VarNameExpression("v"), new ValueExpression(new IntValue(3))),
                new PrintStatement(new VarNameExpression("v")),
                new NopStatement());

        return new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        ifStatement));
    }

    private static Statement getExample6() {
//    Heap handling example
        Statement declaringV = new VarDecStatement("v", new ReferenceType(new IntType()));
        Statement allocatingV = new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)));
        Statement declaringA = new VarDecStatement("a", new ReferenceType(new ReferenceType(new IntType())));
        Statement allocatingA = new HeapAllocationStatement("a", new VarNameExpression("v"));
        Statement allocatingV2 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(30)));
        Statement printingA = new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VarNameExpression("a"))));

        return new CompoundStatement(declaringV,
                new CompoundStatement(allocatingV,
                        new CompoundStatement(declaringA,
                                new CompoundStatement(allocatingA,
                                        new CompoundStatement(allocatingV2,
                                                printingA)))));
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

        return new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        whileStatement));
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

        return new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        new CompoundStatement(forStatement,
                                printingV)));
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


        return new CompoundStatement(declaringV,
                new CompoundStatement(declaringA,
                        new CompoundStatement(assigningV,
                                new CompoundStatement(allocatingA,
                                        new CompoundStatement(thread1,
                                                new CompoundStatement(printingV, printingA))))));
    }
}
