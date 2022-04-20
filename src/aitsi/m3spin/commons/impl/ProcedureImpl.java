package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcedureImpl extends TNodeImpl implements Procedure {
    private static final EntityType TYPE = EntityType.PROCEDURE;

    private List<Statement> stmtList;
    private int id;

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

    public ProcedureImpl(int id){
        this.id = id;
    }

    @Override
    public EntityType getType() {
        return TYPE;
    }

    @Override
    public void setFirstChild(TNode child) {

    }
}
