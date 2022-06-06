package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.StatementList;
import aitsi.m3spin.pkb.model.AttributableNode;
import aitsi.m3spin.pkb.model.StringAttribute;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
public class ProcedureImpl extends TNodeImpl implements Procedure, AttributableNode {
    private StatementList statementList;
    private int id;
    private StringAttribute procName;

    public ProcedureImpl(String name, StatementList statementList) {
        procName = new StringAttribute(name);
        this.statementList = statementList;
    }

    public ProcedureImpl(int id) {
        this.id = id;
    }

    public String getName() {
        return procName.getValue();
    }

    public void setName(String name) {
        procName = new StringAttribute(name);
    }

    @Override
    public EntityType getType() {
        return EntityType.PROCEDURE;
    }

    @Override
    public NodeAttribute getAttribute() {
        return procName;
    }

    @Override
    public void setAttribute(NodeAttribute attribute) {
        this.procName = (StringAttribute) attribute;
    }
}
