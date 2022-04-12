package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Assignment;
import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class AssignmentImpl extends TNodeImpl implements Assignment {
    private Variable var;
    private Expression expr;

    public AssignmentImpl(){
        super(EntityType.ASSIGNMENT);
    }

    public AssignmentImpl(Variable var, Expression expr){
        this();
        this.var = var;
        this.expr = expr;
    }

}
