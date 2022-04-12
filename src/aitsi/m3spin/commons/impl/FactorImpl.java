package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Factor;

public class FactorImpl extends TNodeImpl implements Factor {
    public FactorImpl(){
        super(EntityType.FACTOR);
    }
}
