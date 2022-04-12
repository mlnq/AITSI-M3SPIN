package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Minus;

public class MinusImpl extends TNodeImpl implements Minus {
    public MinusImpl(){
        super(EntityType.MINUS);
    }
}
