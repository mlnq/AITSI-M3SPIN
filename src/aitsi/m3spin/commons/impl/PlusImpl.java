package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Plus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlusImpl extends TNodeImpl implements Plus {
    private static final EntityType TYPE = EntityType.PLUS;

    @Override
    public EntityType getType() {
        return TYPE;
    }
}
