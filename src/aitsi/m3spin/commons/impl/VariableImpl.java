package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
public class VariableImpl extends TNodeImpl implements Variable {
    private static final EntityType TYPE = EntityType.VARIABLE;

    private int id;

    public VariableImpl(String name) {
        super.attribute = name;
    }


    public String getName(){
        return super.attribute;
    }

    public void setName(String name){
        super.attribute = name;
    }

    @Override
    public EntityType getType() {
        return TYPE;
    }
}
