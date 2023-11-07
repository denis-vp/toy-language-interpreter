package view.cli;

import view.cli.commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands = new HashMap<>();

    public void addCommand(Command command) {
        this.commands.put(command.getId(), command);
    }

    private void printMenu() {
        System.out.println("\nMenu:");
        for (Command command : this.commands.values()) {
            System.out.printf("%s: %s%n", command.getId(), command.getDescription());
        }
    }

    public void show() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                this.printMenu();
                System.out.print("Enter an option: ");
                String option = scanner.nextLine();
                Command command = this.commands.get(option);
                if (command == null) {
                    System.out.println("Invalid option!");
                    continue;
                }
                command.execute();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
