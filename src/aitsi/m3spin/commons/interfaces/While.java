package aitsi.m3spin.commons.interfaces;

public interface While extends Statement {
    Variable getConditionVar();

    StatementList getStmtList();
}
