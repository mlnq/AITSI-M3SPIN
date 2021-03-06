package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Assignment;
import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
public class AssignmentImpl extends StatementImpl implements Assignment {

    private Variable variable;
    private Expression expression;

    public EntityType getType() {
        return EntityType.ASSIGNMENT;
    }
}
