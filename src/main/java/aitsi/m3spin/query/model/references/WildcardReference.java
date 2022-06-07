package aitsi.m3spin.query.model.references;

import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;

public class WildcardReference implements RelationshipArgumentRef {
    @Override
    public ReferenceType getArgRefType() {
        return ReferenceType.WILDCARD;
    }
}
