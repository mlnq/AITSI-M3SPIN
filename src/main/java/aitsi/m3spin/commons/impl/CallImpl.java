package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Call;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CallImpl extends StatementImpl implements Call {

    private String procName; //todo zmienic nazwe

    @Override
    public EntityType getType() {
        return EntityType.CALL;
    }
}
