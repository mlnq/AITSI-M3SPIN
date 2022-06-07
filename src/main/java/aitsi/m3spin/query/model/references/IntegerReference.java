package aitsi.m3spin.query.model.references;

import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.pkb.model.IntegerAttribute;
import aitsi.m3spin.query.model.enums.AttributeTypeEnum;
import aitsi.m3spin.query.model.relationships.EntityReference;
import aitsi.m3spin.query.model.relationships.LineReference;
import aitsi.m3spin.query.model.relationships.StatementReference;
import lombok.Getter;

@Getter
public class IntegerReference extends PrimitiveTypeReference
        implements EntityReference, LineReference, StatementReference {
    private final int value;

    public IntegerReference(int value) {
        super(AttributeTypeEnum.INTEGER, ReferenceType.INTEGER);
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public NodeAttribute getAsAttribute() {
        return new IntegerAttribute(this.value);
    }

}
