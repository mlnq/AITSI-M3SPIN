package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;

import java.util.List;

public class ProcedureImpl implements Procedure {
    private String name;
    private List<Statement> stmtList;

    public ProcedureImpl(String name, List<Statement> stmtList) {
        this.name = name;
        this.stmtList = stmtList;
    }
}
