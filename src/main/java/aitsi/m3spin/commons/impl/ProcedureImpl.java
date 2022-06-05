package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.StatementList;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
public class ProcedureImpl extends TNodeImpl implements Procedure {

    private StatementList statementList;
    private int id;

    public ProcedureImpl(String name, StatementList statementList) {
        super.attribute = name;
        this.statementList = statementList;
    }

    public ProcedureImpl(int id) {
        this.id = id;
    }

    public String getName() {
        return super.attribute;
    }

    public void setName(String name) {
        super.attribute = name;
    }

    @Override
    public EntityType getType() {
        return EntityType.PROCEDURE;
    }
}
