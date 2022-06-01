package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Minus;
import aitsi.m3spin.commons.interfaces.TNode;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MinusImpl extends TNodeImpl implements Minus {

    @Override
    public EntityType getType() {
        return EntityType.MINUS;
    }

    @Override
    public void setFirstChild(TNode child) {

    }
}
