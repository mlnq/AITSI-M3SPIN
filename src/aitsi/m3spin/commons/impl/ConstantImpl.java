package aitsi.m3spin.commons.impl;


import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Constant;
import aitsi.m3spin.commons.interfaces.TNode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConstantImpl extends TNodeImpl implements Constant {
    private static final EntityType TYPE = EntityType.CONSTANT;

    private int value;

    @Override
    public EntityType getType() {
        return TYPE;
    }

    @Override
    public void setFirstChild(TNode child) {

    }
}
