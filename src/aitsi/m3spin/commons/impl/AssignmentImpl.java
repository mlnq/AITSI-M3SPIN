package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.Assignment;
import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AssignmentImpl implements Assignment {
    private Variable variable;
    private Expression expression;
}
