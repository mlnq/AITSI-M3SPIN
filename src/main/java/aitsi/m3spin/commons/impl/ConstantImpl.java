package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Constant;
import aitsi.m3spin.commons.interfaces.TNode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
public class ConstantImpl extends TNodeImpl implements Constant {

    private IntegerAttribute value;

    public ConstantImpl(int value) {
        this.value = new IntegerAttribute(value);
    }

    @Override
    public EntityType getType() {
        return EntityType.CONSTANT;
    }
}
