package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Factor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FactorImpl extends TNodeImpl implements Factor {
    private static final EntityType TYPE = EntityType.FACTOR;

    @Override
    public EntityType getType() {
        return TYPE;
    }
}
