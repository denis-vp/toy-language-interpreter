package programs;

import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VarNameExpression;
import model.statement.AssignmentStatement;
import model.statement.CompoundStatement;
import model.statement.PrintStatement;
import model.statement.VarDecStatement;
import model.type.IntType;
import model.value.IntValue;

public class ProgramGenerator {
    public static CompoundStatement getProgram1() {
        return new CompoundStatement(
            new CompoundStatement(new VarDecStatement("a", new IntType()),
                    new AssignmentStatement("a", new ValueExpression(new IntValue(1)))),
            new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new VarNameExpression("a"),
                    new ValueExpression(new IntValue(2)), 1)),
                    new PrintStatement(new VarNameExpression("a")))
        );
    }
}
