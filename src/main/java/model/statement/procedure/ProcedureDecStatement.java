package model.statement.procedure;

import adt.IDictionary;
import adt.Pair;
import exception.StatementException;
import model.ProgramState;
import model.statement.Statement;
import model.type.Type;

import java.util.List;

public class ProcedureDecStatement implements Statement {
    private final String name;

    private final List<String> parameters;

    private final Statement body;

    public ProcedureDecStatement(String name, List<String> parameters, Statement body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, Pair<List<String>, Statement>> procedureTable = state.getProcedureTable();

        if (procedureTable.search(this.name)) {
            throw new StatementException("Procedure " + this.name + " already defined!");
        }
        procedureTable.add(this.name, new Pair<>(this.parameters, this.body));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        List<String> newParameters = this.parameters.stream().toList();
        return new ProcedureDecStatement(this.name, newParameters, this.body.deepCopy());
    }

    public String toString() {
        return "procedure " + this.name + " " + this.parameters + " " + this.body;
    }
}
