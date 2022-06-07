package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.model.StringAttribute;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VariableImpl extends TNodeImpl implements Variable {
    @Getter
    private int id;
    private StringAttribute varName;

    public VariableImpl(int id) {
        this.id = id;
    }

    public VariableImpl(String name) {
        this.varName = new StringAttribute(name);
    }

    @Override
    public String getVarName() {
        return varName.getValue();
    }

    @Override
    public void setVarName(String nameAttr) {
        this.varName = new StringAttribute(nameAttr);
    }

    @Override
    public NodeAttribute getAttribute() {
        return varName;
    }

    @Override
    public void setAttribute(NodeAttribute attribute) {
        varName = (StringAttribute) attribute;
    }

    @Override
    public EntityType getType() {
        return EntityType.VARIABLE;
    }
}
