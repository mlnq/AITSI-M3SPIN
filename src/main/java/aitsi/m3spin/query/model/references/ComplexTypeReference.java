package aitsi.m3spin.query.model.references;

public abstract class ComplexTypeReference extends WithArgumentRef {
    protected ComplexTypeReference(ReferenceType referenceType) {
        super(referenceType);
    }

    public abstract Synonym getSynonym();
}
