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
    public static Statement getExample1() {
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));

        Statement statement = new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        printingV));

        return statement;
    }

    public static Statement getExample2() {
        Statement declaringA = new VarDecStatement("a", new IntType());
        Statement declaringB = new VarDecStatement("b", new IntType());
        Statement assigningA = new AssignmentStatement("a",
                new ArithmeticExpression("+" , new ValueExpression(new IntValue(2)),
                        new ArithmeticExpression("*", new ValueExpression(new IntValue(3)),
                                new ValueExpression(new IntValue(5)))));
        Statement assigningB = new AssignmentStatement("b",
                new ArithmeticExpression("+", new VarNameExpression("a"),
                        new ValueExpression(new IntValue(1))));
        Statement printingB = new PrintStatement(new VarNameExpression("b"));

        Statement statement = new CompoundStatement(declaringA,
                new CompoundStatement(declaringB,
                        new CompoundStatement(assigningA,
                                new CompoundStatement(assigningB,
                                        printingB))));

        return statement;
    }

    public static Statement getExample3() {
        Statement declaringA = new VarDecStatement("a", new BoolType());
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningA = new AssignmentStatement("a", new ValueExpression(new BoolValue(true)));
        Statement ifStatement = new IfStatement(new VarNameExpression("a"),
                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                new AssignmentStatement("v", new ValueExpression(new IntValue(3))));
        Statement printingV = new PrintStatement(new VarNameExpression("v"));

        Statement statement = new CompoundStatement(declaringA,
                new CompoundStatement(declaringV,
                        new CompoundStatement(assigningA,
                                new CompoundStatement(ifStatement,
                                        printingV))));

        return statement;
    }

    public static Statement getExample4() {
        Statement declaringV = new VarDecStatement("v", new StringType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new StringValue("./input/test.in")));
        Statement openingFile = new OpenReadFileStatement(new VarNameExpression("v"));
        Statement declaringC = new VarDecStatement("c", new IntType());
        Statement readingC = new ReadFileStatement(new VarNameExpression("v"), "c");
        Statement printingC = new PrintStatement(new VarNameExpression("c"));
        Statement closingFile = new CloseReadFileStatement(new VarNameExpression("v"));

        Statement statement = new CompoundStatement(declaringV,
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

    public static Statement getExample5() {
        Statement declaringV = new VarDecStatement("v", new IntType());
        Statement assigningV = new AssignmentStatement("v", new ValueExpression(new IntValue(1)));
        Statement ifStatement = new IfStatement(new RelationalExpression("<", new VarNameExpression("v"), new ValueExpression(new IntValue(3))),
                new PrintStatement(new VarNameExpression("v")),
                new NopStatement());

        Statement statement = new CompoundStatement(declaringV,
                new CompoundStatement(assigningV,
                        ifStatement));

        return statement;
    }
}
