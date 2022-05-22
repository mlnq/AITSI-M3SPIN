package aitsi.m3spin.query.model.references;

import lombok.Getter;

@Getter
public class StringReference extends PrimitiveTypeReference {
    private final String value;

    protected StringReference(String value) {
        super(ReferenceType.STRING);
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
