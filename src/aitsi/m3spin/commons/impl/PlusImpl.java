package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Minus;
import aitsi.m3spin.commons.interfaces.Plus;
import lombok.NoArgsConstructor;

public class PlusImpl extends TNodeImpl implements Plus {
    public PlusImpl(){
        super(EntityType.PLUS);
    }
}
