package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Assignment;
import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AssignmentImpl extends TNodeImpl implements Assignment {
    private static final EntityType TYPE = EntityType.ASSIGNMENT;

    private Variable var;
    private Expression expr;

    @Override
    public EntityType getType() {
        return TYPE;
    }
}
