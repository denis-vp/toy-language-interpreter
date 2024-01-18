package view.cli;

import adt.*;
import controller.Controller;
import model.value.Value;
import programgenerator.ProgramGenerator;
import model.ProgramState;
import model.statement.Statement;
import repository.IRepository;
import repository.Repository;
import view.cli.commands.ExitCommand;
import view.cli.commands.RunExampleCommand;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;


public class CliInterpreter {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        CliInterpreter.addCommands(CliInterpreter.getLogFile(), menu);
        menu.show();
    }

    private static String getLogFile() {
        String logFilePath = "./logs/";

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
        List<Statement> programs = ProgramGenerator.getPrograms();
        for (int i = 0; i < programs.size(); i++) {
            Statement program = programs.get(i);

            Stack<IDictionary<String, Value>> symbolTables = new Stack<>();
            symbolTables.push(new MyDictionary<>());
            ProgramState programState = new ProgramState(program, new MyStack<>(), symbolTables,
                    new MyHeap(), new MyConcurrentDictionary<>(), new MyList<>(), new MyLockTable(), new MyLatchTable(),
                    new MySemaphoreTable(), new MyBarrierTable(), new MyProcedureTable());
            IRepository repository = new Repository(programState, logFilePath);
            Controller controller = new Controller(repository, true);

            menu.addCommand(new RunExampleCommand(String.valueOf(i + 1), "run example " + (i + 1), controller));
        }

        menu.addCommand(new ExitCommand("0", "exit"));
    }
}