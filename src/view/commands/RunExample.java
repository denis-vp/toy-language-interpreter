package view.commands;

import controller.Controller;
import exception.MyException;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String id, String description, Controller controller) {
        super(id, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            this.controller.allSteps();
            System.out.println("Program output:\n" + this.controller.getProgramOutput() + "\n");
        } catch (MyException e) {
            System.out.println("Something went wrong! " + e.getMessage());
        }
    }
}
