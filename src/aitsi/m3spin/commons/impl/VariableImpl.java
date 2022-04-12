package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class VariableImpl extends TNodeImpl implements Variable {
    private int ID;

    public VariableImpl(String name) {
        super(EntityType.VARIABLE);
        super.attribute = name;
    }

    public VariableImpl(int ID) {
        super(EntityType.VARIABLE);
        this.ID = ID;
    }
}
