package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.commons.interfaces.While;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class WhileImpl extends TNodeImpl implements While {
    private Variable conditionVar;
    private List<Statement> stmtList;

    public WhileImpl(){
        super(EntityType.WHILE);
    }

    public WhileImpl(Variable conditionVar, List<Statement> stmtList){
        this();
        this.conditionVar = conditionVar;
        this.stmtList = stmtList;
    }
}
