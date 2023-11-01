import examplegenerator.ExampleGenerator;
import view.cli.TextMenu;
import view.cli.commands.Command;
import view.cli.commands.ExitCommand;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

// TODO: Implement the garbage collector

public class Interpreter {
    public static void main(String[] args) {
        String logFilePath = getLogFile();
        TextMenu menu = new TextMenu();

        menu.addCommand(new ExitCommand("0", "exit"));
        for (List<Command> commandList : ExampleGenerator.getExampleCommands(logFilePath)) {
            for (Command command : commandList) {
                menu.addCommand(command);
            }
        }

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
}