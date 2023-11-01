package view.cli.commands;

public abstract class Command {
    private final String id;
    private final String description;

    public Command(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public abstract void execute();

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }
}
