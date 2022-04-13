package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
public class VariableImpl extends TNodeImpl implements Variable {
    private static final EntityType TYPE = EntityType.VARIABLE;

    private int id;

    public VariableImpl(String name) {
        super.attribute = name;
    }

    @Override
    public EntityType getType() {
        return TYPE;
    }
}
