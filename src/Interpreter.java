import controller.Controller;
import datastructure.*;
import model.programstate.ProgramState;
import programgenerator.ProgramGenerator;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.util.Objects;
import java.util.Scanner;

// TODO: Manage better somehow the default values for reference types

public class Interpreter {
    public static void main(String[] args) {
        try {
            String logFilePath = getLogFile();

            ProgramState ex1 = new ProgramState(ProgramGenerator.getExample1(), new MyStack<>(), new MyDictionary<>(),
                    new MyList<>(), new MyHeap<>(), new MyDictionary<>());
            IRepository repository1 = new Repository(ex1, logFilePath);
            Controller controller1 = new Controller(repository1);

            ProgramState ex2 = new ProgramState(ProgramGenerator.getExample2(), new MyStack<>(), new MyDictionary<>(),
                    new MyList<>(), new MyHeap<>(), new MyDictionary<>());
            IRepository repository2 = new Repository(ex2, logFilePath);
            Controller controller2 = new Controller(repository2);

            ProgramState ex3 = new ProgramState(ProgramGenerator.getExample3(), new MyStack<>(), new MyDictionary<>(),
                    new MyList<>(), new MyHeap<>(), new MyDictionary<>());
            IRepository repository3 = new Repository(ex3, logFilePath);
            Controller controller3 = new Controller(repository3);

            ProgramState ex4 = new ProgramState(ProgramGenerator.getExample4(), new MyStack<>(), new MyDictionary<>(),
                    new MyList<>(), new MyHeap<>(), new MyDictionary<>());
            IRepository repository4 = new Repository(ex4, logFilePath);
            Controller controller4 = new Controller(repository4);

            ProgramState ex5 = new ProgramState(ProgramGenerator.getExample5(), new MyStack<>(), new MyDictionary<>(),
                    new MyList<>(), new MyHeap<>(), new MyDictionary<>());
            IRepository repository5 = new Repository(ex5, logFilePath);
            Controller controller5 = new Controller(repository5);

            TextMenu menu = new TextMenu();
            menu.addCommand(new RunExample("1", "run example 1", controller1));
            menu.addCommand(new RunExample("2", "run example 2", controller2));
            menu.addCommand(new RunExample("3", "run example 3", controller3));
            menu.addCommand(new RunExample("4", "run example 4", controller4));
            menu.addCommand(new RunExample("5", "run example 5", controller5));
//            menu.addCommand(new ToggleDisplayFlagCommand("1.1", "toggle display flag for example 1", controller1));
//            menu.addCommand(new ToggleDisplayFlagCommand("2.1", "toggle display flag for example 2", controller2));
//            menu.addCommand(new ToggleDisplayFlagCommand("3.1", "toggle display flag for example 3", controller3));
//            menu.addCommand(new ToggleDisplayFlagCommand("4.1", "toggle display flag for example 4", controller4));
//            menu.addCommand(new ToggleDisplayFlagCommand("5.1", "toggle display flag for example 5", controller5));
            menu.addCommand(new ExitCommand("x", "exit"));
            menu.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getLogFile() {
        String logFilePath = "./log/";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the log file name: ");
        String input = scanner.nextLine();

        if (Objects.equals(input, "")) {
            logFilePath += "log.txt";
        } else  {
            logFilePath += input;
        }

        return logFilePath;
    }
}