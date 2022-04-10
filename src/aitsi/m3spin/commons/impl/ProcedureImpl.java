package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProcedureImpl extends TNodeImpl implements Procedure {
    private List<Statement> stmtList;
    private int ID;

    public String getName(){
        return super.attribute;
    }

    public void setName(String name){
        super.attribute = name;
    }

    public ProcedureImpl(String name, List<Statement> stmtList) {
        super.attribute = name;
        this.stmtList = stmtList;
    }

    public ProcedureImpl(int ID){
        this.ID = ID;
    }
}
