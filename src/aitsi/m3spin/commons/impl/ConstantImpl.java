package aitsi.m3spin.commons.impl;


import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Constant;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class ConstantImpl extends TNodeImpl implements Constant {
    private int value;

    public ConstantImpl(){
        super(EntityType.CONSTANT);
    }

    public ConstantImpl(int value){
        this();
        this.value = value;
    }
}
