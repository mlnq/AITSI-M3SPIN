package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.commons.interfaces.While;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
public class WhileImpl implements While {
    private Variable conditionVar;
    private List<Statement> stmtList;
}
