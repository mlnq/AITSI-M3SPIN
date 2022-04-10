package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProcedureImpl implements Procedure {
    private String name;
    private List<Statement> stmtList;
}
