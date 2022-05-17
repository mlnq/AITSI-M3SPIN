package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Factor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FactorImpl extends TNodeImpl implements Factor {

    @Override
    public EntityType getType() {
        return EntityType.FACTOR;
    }
}
