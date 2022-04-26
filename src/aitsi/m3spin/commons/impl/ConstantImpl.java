package aitsi.m3spin.commons.impl;


import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Constant;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
public class ConstantImpl extends TNodeImpl implements Constant {
    private static final EntityType TYPE = EntityType.CONSTANT;

    private int value;

    @Override
    public EntityType getType() {
        return TYPE;
    }
}
