package aitsi.m3spin.commons.impl;

import java.util.List;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.StatementList;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StatementListImpl implements StatementList {
    private List<Statement> stmtList;
}
