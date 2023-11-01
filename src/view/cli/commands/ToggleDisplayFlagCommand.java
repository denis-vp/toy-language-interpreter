package view.cli.commands;

import controller.Controller;

public class ToggleDisplayFlagCommand extends Command {
    private final Controller controller;

    public ToggleDisplayFlagCommand(String id, String description, Controller controller) {
        super(id, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        this.controller.toggleDisplayFlag();
        System.out.println("Display flag set to " + this.controller.getDisplayFlag() + "\n");
    }
}
