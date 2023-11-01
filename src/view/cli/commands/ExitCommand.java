package view.cli.commands;

public class ExitCommand extends Command {
    public ExitCommand(String id, String description) {
        super(id, description);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
