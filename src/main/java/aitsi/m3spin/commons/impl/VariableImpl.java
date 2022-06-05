package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.model.AttributableNode;
import aitsi.m3spin.pkb.model.StringAttribute;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
public class VariableImpl extends TNodeImpl implements Variable, AttributableNode {

    private int id;
    private StringAttribute nameAttr;

    public VariableImpl(int id) {
        this.id = id;
    }

    public VariableImpl(String name) {
        this.nameAttr = new StringAttribute(name);
    }

    public String getNameAttr() {
        return nameAttr.getValue();
    }

    public void setName(String nameAttr) {
        new StringAttribute(nameAttr);
    }

    @Override
    public NodeAttribute getAttribute() {
        return nameAttr;
    }

    @Override
    public void setAttribute(NodeAttribute attribute) {
        nameAttr = (StringAttribute) attribute;
    }

    @Override
    public EntityType getType() {
        return EntityType.VARIABLE;
    }
}
