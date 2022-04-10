package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.Getter;

@Getter
public class VariableImpl extends TNodeImpl implements Variable {
    private int ID;

    public String getName(){
        return super.attribute;
    }

    public void setName(String name){
        super.attribute = name;
    }

    public VariableImpl(String name) {
        super.attribute = name;
    }

    public VariableImpl(int ID){
        this.ID = ID;
    }
}
