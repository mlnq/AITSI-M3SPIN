package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.pkb.model.IntegerAttribute;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public abstract class StatementImpl extends TNodeImpl implements Statement {
    protected IntegerAttribute stmtLine = new IntegerAttribute(-1);//todo ATS-19 - Szymon

    @Override
    public int getStmtLine() {
        return stmtLine.getValue();
    }

    @Override
    public void setStmtLine(int stmtLine) {
        this.stmtLine = new IntegerAttribute(stmtLine);
    }

}
