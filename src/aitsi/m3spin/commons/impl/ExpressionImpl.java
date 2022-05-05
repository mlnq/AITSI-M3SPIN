package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Factor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpressionImpl extends TNodeImpl implements Expression {

    private Factor factor;
    private Expression expression;

    public ExpressionImpl(Factor factor) {
        this.factor = factor;
        this.expression = null;
    }

    @Override
    public EntityType getType() {
        return EntityType.EXPRESSION;
    }
}
