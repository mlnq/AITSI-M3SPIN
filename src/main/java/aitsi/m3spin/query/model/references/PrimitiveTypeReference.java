package aitsi.m3spin.query.model.references;

import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.query.model.enums.AttributeTypeEnum;
import aitsi.m3spin.query.model.enums.WithArgRefType;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import lombok.Getter;

@Getter
public abstract class PrimitiveTypeReference extends WithArgumentRef implements NodeAttribute, RelationshipArgumentRef {
    private final ReferenceType argRefType;

    protected PrimitiveTypeReference(AttributeTypeEnum withRefType, ReferenceType argRefType) {
        super(withRefType, WithArgRefType.PRIMITIVE);
        this.argRefType = argRefType;
    }

    @Override
    public boolean equalsToSynonym(Synonym synonym) {
        return false;
    }

    public abstract NodeAttribute getAsAttribute();
}
