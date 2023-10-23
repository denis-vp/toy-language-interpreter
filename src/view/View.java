package view;

import controller.Controller;
import exception.MyException;
import programgenerator.ProgramGenerator;

import java.util.Scanner;

public class View {
    private boolean chosenProgram = false;
    private final Controller controller;
    private static final Scanner scanner = new Scanner(System.in);

    public View(Controller controller) {
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
                        this.chooseProgram();
                        break;

                    case 2:
                        this.runProgram();
                        break;

                    case 3:
                        this.toggleDisplayFlag();
                        break;

                    case 4:
                        System.out.println("Program output:\n" + this.controller.getProgramOutput() + "\n");
                        break;

                    case 0:
                        System.out.println("Bye!");
                        return;

                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong! " + e.getMessage());
                View.scanner.nextLine();
            }
        }
    }

    private void chooseProgram() throws MyException {
        System.out.print("Please enter the program number (0 to 3): ");
        int chosenProgram = Integer.parseInt(View.scanner.nextLine());
        switch (chosenProgram) {
            case 0:
                this.controller.setProgram(ProgramGenerator.getProgram0());
                this.chosenProgram = true;
                break;
            case 1:
                this.controller.setProgram(ProgramGenerator.getProgram1());
                this.chosenProgram = true;
                break;

            case 2:
                this.controller.setProgram(ProgramGenerator.getProgram2());
                this.chosenProgram = true;
                break;

            case 3:
                this.controller.setProgram(ProgramGenerator.getProgram3());
                this.chosenProgram = true;
                break;

            default:
                System.out.println("\nInvalid program number!");
        }
    }

    private void runProgram() {
        if (!this.chosenProgram) {
            System.out.println("\nPlease choose a program first!");
            return;
        }

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
        System.out.println("4. Print program output");
        System.out.println("0. Exit");
    }

    private static int getOption() {
        System.out.print("Please enter an option: ");
        int option = Integer.parseInt(View.scanner.nextLine());
        return option;
    }
}
