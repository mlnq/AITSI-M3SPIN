package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.If;
import aitsi.m3spin.commons.interfaces.StatementList;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IfImpl extends StatementImpl implements If {
    @Override
    public EntityType getType() {
        return EntityType.IF;
    }

    private Variable conditionVar;
    private StatementList thenStmtList;
    private StatementList elseStmtList;
}
