package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.If;

public class IfImpl extends TNodeImpl implements If {
    public IfImpl(){
        super(EntityType.IF);
    }
}
