package aitsi.m3spin.query.model.references;

import aitsi.m3spin.query.model.relationships.EntityReference;
import aitsi.m3spin.query.model.relationships.LineReference;
import aitsi.m3spin.query.model.relationships.StatementReference;
import aitsi.m3spin.query.model.relationships.VariableReference;

public class WildcardReference implements EntityReference, VariableReference, LineReference, StatementReference {
    @Override
    public ReferenceType getReferenceType() {
        return ReferenceType.WILDCARD;
    }
}
