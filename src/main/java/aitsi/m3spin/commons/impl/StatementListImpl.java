package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.StatementList;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
public class StatementListImpl extends TNodeImpl implements StatementList {

    private List<Statement> statements;

    @Override
    public EntityType getType() {
        return EntityType.STMT_LIST;
    }
}
