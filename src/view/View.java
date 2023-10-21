package view;

import controller.Controller;
import exception.MyException;

import java.util.Scanner;

public class View {

    private Controller controller;
    private static final Scanner scanner = new Scanner(System.in);

    public View(Controller controller, String programsPath) {
        this.controller = controller;
    }

    public void run() {
        View.printTitle();

        int option;
        while (true) {
            View.printOptions();

            try {
                option = View.getOption();
                System.out.println();
                switch (option) {
                    case 1:
//                        this.chooseProgram();
                        break;

                    case 2:
                        this.runProgram();
                        break;

                    case 3:
                        this.toggleDisplayFlag();
                        break;

                    case 0:
                        System.out.println("\nBye!");
                        return;

                    default:
                        System.out.println("\nInvalid option!");
                }
//            } catch (MyException e) {
//                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Something went wrong!");
                View.scanner.nextLine();
            }
        }
    }

    private void runProgram() {
        try {
            this.controller.allSteps();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void toggleDisplayFlag() {
        this.controller.toggleDisplayFlag();
    }

    private static void printTitle() {
        System.out.println("|----------------------|");
        System.out.println("|- Temp Language Name -|");
        System.out.println("|----------------------|");
    }

    private static void printOptions() {
        System.out.println("1. Choose program");
        System.out.println("2. Run");
        System.out.println("3. Toggle display flag");
        System.out.println("0. Exit");
    }

    private static int getOption() {
        System.out.print("Please enter an option: ");
        int option = Integer.parseInt(View.scanner.nextLine());
        return option;
    }
}
