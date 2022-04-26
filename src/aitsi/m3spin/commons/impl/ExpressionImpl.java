package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.Expression;
import aitsi.m3spin.commons.interfaces.Factor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpressionImpl implements Expression {
    private Factor factor;
    private Expression expression;

    public ExpressionImpl(Factor factor) {
        this.factor = factor;
        this.expression = null;
    }
}
