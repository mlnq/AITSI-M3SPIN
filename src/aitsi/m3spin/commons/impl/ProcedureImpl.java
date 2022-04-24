package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.StatementList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProcedureImpl implements Procedure {
    private String name;
    private StatementList statementList;
}
