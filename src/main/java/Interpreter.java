import view.cli.CliInterpreter;
import view.gui.GuiInterpreter;

public class Interpreter {
    public static void main(String[] args) {
        if (args.length == 0) {
            CliInterpreter.main(args);
//            GuiInterpreter.main(args);
        }
    }
}
