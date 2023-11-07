package view.cli;

import adt.MyDictionary;
import adt.MyHeap;
import adt.MyList;
import adt.MyStack;
import controller.Controller;
import examplegenerator.ExampleGenerator;
import model.ProgramState;
import model.statement.Statement;
import repository.IRepository;
import repository.Repository;
import view.cli.commands.ExitCommand;
import view.cli.commands.RunExampleCommand;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class CliInterpreter {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        CliInterpreter.addCommands(CliInterpreter.getLogFile(), menu);
        menu.show();
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

    private static void addCommands(String logFilePath, TextMenu menu) {
        List<Statement> examples = ExampleGenerator.getExamples();
        for (int i = 0; i < examples.size(); i++) {
            Statement example = examples.get(i);

            ProgramState programState = new ProgramState(example, new MyStack<>(), new MyDictionary<>(),
                    new MyHeap(), new MyDictionary<>(), new MyList<>());
            IRepository repository = new Repository(programState, logFilePath);
            Controller controller = new Controller(repository);

            menu.addCommand(new RunExampleCommand(String.valueOf(i + 1), "run example " + (i + 1), controller));
        }

        menu.addCommand(new ExitCommand("0", "exit"));
    }
}