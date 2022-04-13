package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Factor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionImpl extends TNodeImpl implements  Expression{
    private static final EntityType TYPE = EntityType.EXPRESSION;

    private Factor factor;

    private Expression expression;

    @Override
    public EntityType getType() {
        return TYPE;
    }
}
