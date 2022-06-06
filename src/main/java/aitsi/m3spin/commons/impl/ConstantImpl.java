package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Constant;
import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.pkb.model.AttributableNode;
import aitsi.m3spin.pkb.model.IntegerAttribute;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
public class ConstantImpl extends TNodeImpl implements Constant, AttributableNode {

    private IntegerAttribute value;

    public ConstantImpl(int value) {
        this.value = new IntegerAttribute(value);
    }

    @Override
    public EntityType getType() {
        return EntityType.CONSTANT;
    }

    @Override
    public NodeAttribute getAttribute() {
        return value;
    }

    @Override
    public void setAttribute(NodeAttribute attribute) {
        this.value = (IntegerAttribute) attribute;
    }
}
