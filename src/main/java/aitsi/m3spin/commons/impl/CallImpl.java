package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Call;
import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.pkb.model.AttributableNode;
import aitsi.m3spin.pkb.model.StringAttribute;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CallImpl extends StatementImpl implements Call, AttributableNode {
    private StringAttribute procName;

    @Override
    public EntityType getType() {
        return EntityType.CALL;
    }

    @Override
    public String getProcName() {
        return procName.getValue();
    }

    @Override
    public void setProcName(String procName) {
        this.procName = new StringAttribute(procName);
    }

    @Override//todo zostaje kwestia get i set attribute dla Call. ATS-26
    // Call ma 2 atrubuty procName i stmt# odziedziczony z Statement
    public NodeAttribute getAttribute() {
        return procName;
    }

    @Override
    public void setAttribute(NodeAttribute attribute) {
        this.procName = (StringAttribute) attribute;
    }
}
