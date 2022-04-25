package aitsi.m3spin.pkb.impl.helpers;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.Getter;

public class ProcedureVariableRelation {

    @Getter
    private Procedure procedure;
    @Getter
    private Variable variable;

    public ProcedureVariableRelation(Procedure procedure, Variable variable){
        this.procedure = procedure;
        this.variable = variable;
    }


}
