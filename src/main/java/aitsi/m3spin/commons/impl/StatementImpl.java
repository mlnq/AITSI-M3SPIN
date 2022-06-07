package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Statement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public abstract class StatementImpl extends TNodeImpl implements Statement  {
    protected int stmtLine;

    @Override
    public EntityType getType() {
        return EntityType.STMT;
    }

}
