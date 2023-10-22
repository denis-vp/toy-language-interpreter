// TODO: make better toString() functions for ExecutionStack, SymbolTable, Output
// TODO: hardcode some programs

import repository.Repository;
import controller.Controller;
import view.View;

public class Main {
    public static void main(String[] args) {
        Repository repository = new Repository();
        Controller controller = new Controller(repository);
        View view = new View(controller);
        view.run();
    }
}