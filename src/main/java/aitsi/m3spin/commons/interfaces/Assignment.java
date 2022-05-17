package aitsi.m3spin.commons.interfaces;

public interface Assignment extends Statement {
    Variable getVariable();

    Expression getExpression();
}
