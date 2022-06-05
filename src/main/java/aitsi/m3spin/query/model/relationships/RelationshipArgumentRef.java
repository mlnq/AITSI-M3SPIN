package aitsi.m3spin.query.model.relationships;

import aitsi.m3spin.query.model.references.ReferenceType;

public interface RelationshipArgumentRef {
    //todo zaimplementować usesSynonym() w dziedziczących klasach?
    // po co? już nie pamiętam
    ReferenceType getReferenceType();
}
