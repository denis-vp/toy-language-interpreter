package programgenerator;

import datastructure.*;
import exception.MyException;
import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VarNameExpression;
import model.programstate.ProgramState;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class ProgramGenerator {
    public static ProgramState getProgram0() throws MyException {
        IStatement statement = new CompoundStatement(
                new CompoundStatement(new VarDecStatement("a", new IntType()),
                        new AssignmentStatement("a", new ValueExpression(new IntValue(1)))),
                new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new VarNameExpression("a"),
                        new ValueExpression(new IntValue(2)), 1)),
                        new PrintStatement(new VarNameExpression("a")))
        );
        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyIList<Value> output = new MyList<>();

        ProgramState program;
        try {
            program = new ProgramState(stack, symbolTable, output, statement);
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
        return program;
    }

    public static ProgramState getProgram1() throws MyException {
        IStatement statement = new CompoundStatement(new VarDecStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v",
                        new ValueExpression(new IntValue(2))), new PrintStatement(new VarNameExpression("v"))));

        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyIList<Value> output = new MyList<>();

        ProgramState program;
        try {
            program = new ProgramState(stack, symbolTable, output, statement);
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
        return program;
    }

    public static ProgramState getProgram2() throws MyException {
        IStatement statement = new CompoundStatement(new VarDecStatement("a", new IntType()),
                new CompoundStatement(new VarDecStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a",
                                new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)), 3), 1)),
                                new CompoundStatement(new AssignmentStatement("b",
                                        new ArithmeticExpression(new VarNameExpression("a"), new ValueExpression(new IntValue(1)), 1)),
                                        new PrintStatement(new VarNameExpression("b"))))));

        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyIList<Value> output = new MyList<>();

        ProgramState program;
        try {
            program = new ProgramState(stack, symbolTable, output, statement);
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
        return program;
    }

    public static ProgramState getProgram3() throws MyException {
        IStatement statement = new CompoundStatement(new VarDecStatement("a", new BoolType()),
                new CompoundStatement(new VarDecStatement("v",
                        new IntType()), new CompoundStatement(new AssignmentStatement("a",
                        new ValueExpression(new BoolValue(true))),
                        new CompoundStatement(new IfStatement(new VarNameExpression("a"),
                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                new PrintStatement(new VarNameExpression("v"))))));

        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyIList<Value> output = new MyList<>();

        ProgramState program;
        try {
            program = new ProgramState(stack, symbolTable, output, statement);
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
        return program;
    }
}
