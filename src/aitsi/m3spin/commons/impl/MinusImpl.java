package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Minus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MinusImpl extends TNodeImpl implements Minus {
    private static final EntityType TYPE = EntityType.MINUS;

    @Override
    public EntityType getType() {
        return TYPE;
    }
}
