package aitsi.m3spin.commons.interfaces;

public interface If extends Statement {
    Variable getConditionVar();
    StatementList getThenStmtList();
    StatementList getElseStmtList();
}
