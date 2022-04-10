package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.commons.interfaces.While;

import java.util.List;

public class WhileImpl implements While {
    private Variable conditionVar;
    private List<Statement> stmtList;

    public WhileImpl(Variable conditionVar, List<Statement> stmtList) {
        this.conditionVar = conditionVar;
        this.stmtList = stmtList;
    }
}
