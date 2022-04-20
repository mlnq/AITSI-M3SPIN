package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Factor;
import aitsi.m3spin.commons.interfaces.TNode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpressionImpl extends TNodeImpl implements  Expression{
    private static final EntityType TYPE = EntityType.EXPRESSION;

    private Factor factor;

    private Expression expression;

    @Override
    public EntityType getType() {
        return TYPE;
    }

    @Override
    public void setFirstChild(TNode child) {

    }
}
