import repository.Repository;
import controller.Controller;
import view.View;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Repository repository = new Repository(Main.getLogFile());
        Controller controller = new Controller(repository);
        View view = new View(controller);
        view.run();
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
}