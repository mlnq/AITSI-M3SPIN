package aitsi.m3spin.commons;

import aitsi.m3spin.commons.interfaces.Assignment;
import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Variable;

public class AssignmentImpl implements Assignment {
    private Variable var;
    private Expression expr;

    public AssignmentImpl(Variable var, Expression expr) {
        this.var = var;
        this.expr = expr;
    }
}
