package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.pkb.model.IntegerAttribute;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public abstract class StatementImpl extends TNodeImpl implements Statement {
    protected IntegerAttribute progLine = new IntegerAttribute(0);

    @Override
    public int getProgLine() {
        return progLine.getValue();
    }

    @Override
    public void setProgLine(int progLine) {
        this.progLine = new IntegerAttribute(progLine);
    }

    @Override
    public NodeAttribute getAttribute() {
        return progLine;
    }

    @Override
    public void setAttribute(NodeAttribute attribute) {
        this.progLine = (IntegerAttribute) attribute;
    }

    @Override
    public EntityType getType() {
        return EntityType.STATEMENT;
    }
}
