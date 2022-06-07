package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Factor;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpressionImpl extends TNodeImpl implements Expression {

    private Factor factor;
    private EntityType symbol;
    private Expression expression;
    private int hierarchy;


    public ExpressionImpl(Factor factor, int hierarchy) {
        this.factor = factor;
        this.expression = null;
        this.symbol = null;
        this.hierarchy = hierarchy;

    }
    public ExpressionImpl(){
        this.symbol = null;
        this.expression = null;
        this.factor = null;
        this.hierarchy = 0;
    }

    @Override
    public EntityType getType() {
        return EntityType.EXPRESSION;
    }
}
