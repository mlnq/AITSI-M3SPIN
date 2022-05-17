package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Plus;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlusImpl extends TNodeImpl implements Plus {

    @Override
    public EntityType getType() {
        return EntityType.PLUS;
    }
}
