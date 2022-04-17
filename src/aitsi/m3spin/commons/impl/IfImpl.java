package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.If;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IfImpl extends TNodeImpl implements If {
    private static final EntityType TYPE = EntityType.IF;

    @Override
    public EntityType getType() {
        return TYPE;
    }
}
