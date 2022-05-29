package aitsi.m3spin.query.model.references;

import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.pkb.model.StringAttribute;
import aitsi.m3spin.query.model.relationships.EntityReference;
import aitsi.m3spin.query.model.relationships.VariableReference;
import lombok.Getter;

@Getter
public class StringReference extends PrimitiveTypeReference implements VariableReference, EntityReference {
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
    public NodeAttribute getAsAttribute() {
        return new StringAttribute(this.value);
    }

    @Override
    public boolean isConstantValue() {
        return true;
    }
}
