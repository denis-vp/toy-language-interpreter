import controller.Controller;
import datastructure.*;
import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VarNameExpression;
import model.programstate.ProgramState;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {
        try {
            String logFilePath = getLogFile();

            ProgramState ex1 = new ProgramState(new MyStack<>(), new MyDictionary<>(),
                    new MyList<>(), new MyDictionary<>(), getExample1());
            Repository repository1 = new Repository(ex1, logFilePath);
            Controller controller1 = new Controller(repository1);

            ProgramState ex2 = new ProgramState(new MyStack<>(), new MyDictionary<>(),
                    new MyList<>(), new MyDictionary<>(), getExample2());
            Repository repository2 = new Repository(ex2, logFilePath);
            Controller controller2 = new Controller(repository2);

            ProgramState ex3 = new ProgramState(new MyStack<>(), new MyDictionary<>(),
                    new MyList<>(), new MyDictionary<>(), getExample3());
            Repository repository3 = new Repository(ex3, logFilePath);
            Controller controller3 = new Controller(repository3);

            ProgramState ex4 = new ProgramState(new MyStack<>(), new MyDictionary<>(),
                    new MyList<>(), new MyDictionary<>(), getExample4());
            Repository repository4 = new Repository(ex4, logFilePath);
            Controller controller4 = new Controller(repository4);

            TextMenu menu = new TextMenu();
            menu.addCommand(new RunExample("1", "run example 1", controller1));
            menu.addCommand(new RunExample("2", "run example 2", controller2));
            menu.addCommand(new RunExample("3", "run example 3", controller3));
            menu.addCommand(new RunExample("4", "run example 4", controller4));
//            menu.addCommand(new ToggleDisplayFlagCommand("1.1", "toggle display flag for example 1", controller1));
//            menu.addCommand(new ToggleDisplayFlagCommand("2.1", "toggle display flag for example 2", controller2));
//            menu.addCommand(new ToggleDisplayFlagCommand("3.1", "toggle display flag for example 3", controller3));
//            menu.addCommand(new ToggleDisplayFlagCommand("4.1", "toggle display flag for example 4", controller4));
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getLogFile() {
        String logFilePath = "./log/";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the log file name: ");
        String input = "";
        while (input.isEmpty()) {
            input = scanner.nextLine();
        }
        logFilePath += input;
        return logFilePath;
    }

    public static IStatement getExample1() {
        IStatement statement = new CompoundStatement(new VarDecStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v",
                        new ValueExpression(new IntValue(2))), new PrintStatement(new VarNameExpression("v"))));

        return statement;
    }

    public static IStatement getExample2() {
        IStatement statement = new CompoundStatement(new VarDecStatement("a", new IntType()),
                new CompoundStatement(new VarDecStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a",
                                new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)), 3), 1)),
                                new CompoundStatement(new AssignmentStatement("b",
                                        new ArithmeticExpression(new VarNameExpression("a"), new ValueExpression(new IntValue(1)), 1)),
                                        new PrintStatement(new VarNameExpression("b"))))));

        return statement;
    }

    public static IStatement getExample3() {
        IStatement statement = new CompoundStatement(new VarDecStatement("a", new BoolType()),
                new CompoundStatement(new VarDecStatement("v",
                        new IntType()), new CompoundStatement(new AssignmentStatement("a",
                        new ValueExpression(new BoolValue(true))),
                        new CompoundStatement(new IfStatement(new VarNameExpression("a"),
                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                new PrintStatement(new VarNameExpression("v"))))));

        return statement;
    }

    public static IStatement getExample4() {
        IStatement statement = new CompoundStatement(new VarDecStatement("a", new BoolType()),
                new CompoundStatement(new VarDecStatement("v",
                        new IntType()), new CompoundStatement(new AssignmentStatement("a",
                        new ValueExpression(new BoolValue(true))),
                        new CompoundStatement(new IfStatement(new VarNameExpression("a"),
                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                new PrintStatement(new VarNameExpression("v"))))));

        return statement;
    }
}