package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.Assignment;
import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AssignmentImpl extends TNodeImpl implements Assignment {
    private Variable var;
    private Expression expr;
}
