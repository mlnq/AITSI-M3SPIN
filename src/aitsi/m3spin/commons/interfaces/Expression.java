package aitsi.m3spin.commons.interfaces;

public interface Expression extends TNode {
    Factor getFactor();

    Expression getExpression();
}
