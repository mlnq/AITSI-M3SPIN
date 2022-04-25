package aitsi.m3spin.pkb.impl.helpers;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.Getter;

public class StatementVariableRelation {

    @Getter
    private Statement statement;
    @Getter
    private Variable variable;

    public StatementVariableRelation(Statement statement, Variable variable){
        this.statement = statement;
        this.variable = variable;
    }


}
