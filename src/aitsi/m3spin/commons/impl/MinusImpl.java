package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Minus;
import aitsi.m3spin.commons.interfaces.TNode;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MinusImpl extends TNodeImpl implements Minus {
    private static final EntityType TYPE = EntityType.MINUS;

    @Override
    public EntityType getType() {
        return TYPE;
    }

    @Override
    public void setFirstChild(TNode child) {

    }
}
