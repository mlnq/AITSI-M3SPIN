package aitsi.m3spin.query.model.references;

import lombok.Getter;

@Getter
public class IntegerReference extends PrimitiveTypeReference {
    private final int value;

    protected IntegerReference(int value) {
        super(ReferenceType.INTEGER);
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public boolean isConstantValue() {
        return true;
    }
}
