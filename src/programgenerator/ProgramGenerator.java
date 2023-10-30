package programgenerator;

import model.expression.ArithmeticExpression;
import model.expression.RelationalExpression;
import model.expression.ValueExpression;
import model.expression.VarNameExpression;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

public class ProgramGenerator {
    public static IStatement getExample1() {
        IStatement declaringV = new VarDecStatement("v", new IntType());
        IStatement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        IStatement printingV = new PrintStatement(new VarNameExpression("v"));

        IStatement statement = new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        printingV));

        return statement;
    }

    public static IStatement getExample2() {
        IStatement declaringA = new VarDecStatement("a", new IntType());
        IStatement declaringB = new VarDecStatement("b", new IntType());
        IStatement assigningA = new AssignmentStatement("a",
                new ArithmeticExpression("+" , new ValueExpression(new IntValue(2)),
                        new ArithmeticExpression("*", new ValueExpression(new IntValue(3)),
                                new ValueExpression(new IntValue(5)))));
        IStatement assigningB = new AssignmentStatement("b",
                new ArithmeticExpression("+", new VarNameExpression("a"),
                        new ValueExpression(new IntValue(1))));
        IStatement printingB = new PrintStatement(new VarNameExpression("b"));

        IStatement statement = new CompoundStatement(declaringA,
                new CompoundStatement(declaringB,
                        new CompoundStatement(assigningA,
                                new CompoundStatement(assigningB,
                                        printingB))));

        return statement;
    }

    public static IStatement getExample3() {
        IStatement declaringA = new VarDecStatement("a", new BoolType());
        IStatement declaringV = new VarDecStatement("v", new IntType());
        IStatement assigningA = new AssignmentStatement("a", new ValueExpression(new BoolValue(true)));
        IStatement ifStatement = new IfStatement(new VarNameExpression("a"),
                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                new AssignmentStatement("v", new ValueExpression(new IntValue(3))));
        IStatement printingV = new PrintStatement(new VarNameExpression("v"));

        IStatement statement = new CompoundStatement(declaringA,
                new CompoundStatement(declaringV,
                        new CompoundStatement(assigningA,
                                new CompoundStatement(ifStatement,
                                        printingV))));

        return statement;
    }

    public static IStatement getExample4() {
        IStatement declaringV = new VarDecStatement("v", new StringType());
        IStatement assigningV = new AssignmentStatement("v", new ValueExpression(new StringValue("./input/test.in")));
        IStatement openingFile = new OpenReadFileStatement(new VarNameExpression("v"));
        IStatement declaringC = new VarDecStatement("c", new IntType());
        IStatement readingC = new ReadFileStatement(new VarNameExpression("v"), "c");
        IStatement printingC = new PrintStatement(new VarNameExpression("c"));
        IStatement closingFile = new CloseReadFileStatement(new VarNameExpression("v"));

        IStatement statement = new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        new CompoundStatement(openingFile,
                                new CompoundStatement(declaringC,
                                        new CompoundStatement(readingC,
                                                new CompoundStatement(printingC,
                                                        new CompoundStatement(readingC,
                                                                new CompoundStatement(printingC,
                                                                        closingFile))))))));

        return statement;
    }

    public static IStatement getExample5() {
        IStatement declaringV = new VarDecStatement("v", new IntType());
        IStatement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(1)));
        IStatement ifStatement = new IfStatement(new RelationalExpression("<", new VarNameExpression("v"), new ValueExpression(new IntValue(3))),
                new PrintStatement(new VarNameExpression("v")),
                new NopStatement());

        IStatement statement = new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        ifStatement));

        return statement;
    }
}
