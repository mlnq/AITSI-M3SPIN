package aitsi.m3spin.query.model.references;

import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisonException;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;

public abstract class PrimitiveTypeReference extends WithArgumentRef implements NodeAttribute, RelationshipArgumentRef {
    protected PrimitiveTypeReference(ReferenceType referenceType) {
        super(referenceType);
    }

    @Override
    public boolean equalsToSynonym(Synonym synonym) throws IncompatibleTypesComparisonException {
        throw new IncompatibleTypesComparisonException(synonym.getReferenceType(), this.getReferenceType());
    }

    public abstract NodeAttribute getAsAttribute();
}
