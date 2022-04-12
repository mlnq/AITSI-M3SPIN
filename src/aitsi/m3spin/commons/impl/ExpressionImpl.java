package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Factor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpressionImpl extends TNodeImpl implements  Expression{
    private Factor factor;

    private Expression expression;

    public ExpressionImpl(){

        super(EntityType.EXPRESSION);
    }

    public ExpressionImpl(Factor factor, Expression expression){
        this();
        this.factor = factor;
        this.expression = expression;
    }
}
