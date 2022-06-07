package aitsi.m3spin.query.model.references;

import aitsi.m3spin.query.model.enums.AttributeTypeEnum;
import aitsi.m3spin.query.model.enums.WithArgRefType;

public abstract class ComplexTypeReference extends WithArgumentRef {
    protected ComplexTypeReference(AttributeTypeEnum withValueType, WithArgRefType withArgRefType) {
        super(withValueType, withArgRefType);
    }

    public abstract Synonym getSynonym();
}
