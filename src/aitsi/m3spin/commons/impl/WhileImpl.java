package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.StatementList;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.commons.interfaces.While;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Getter
public class WhileImpl implements While {
    private Variable conditionVar;
    private StatementList stmtList;
}
