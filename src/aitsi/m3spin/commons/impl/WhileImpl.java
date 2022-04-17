package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.commons.interfaces.While;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WhileImpl extends TNodeImpl implements While {
    private static final EntityType TYPE = EntityType.WHILE;
    private Variable conditionVar;
    private List<Statement> stmtList;

    @Override
    public EntityType getType() {
        return TYPE;
    }
}
