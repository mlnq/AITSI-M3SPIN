package aitsi.m3spin.query.model.references;

import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisonException;

public abstract class PrimitiveTypeReference extends Reference {
    protected PrimitiveTypeReference(ReferenceType referenceType) {
        super(referenceType);
    }

    @Override
    public boolean equalsToSynonym(Synonym synonym) throws IncompatibleTypesComparisonException {
        throw new IncompatibleTypesComparisonException(synonym.getReferenceType(), this.getReferenceType());
    }

    public abstract Object getValue();
}
