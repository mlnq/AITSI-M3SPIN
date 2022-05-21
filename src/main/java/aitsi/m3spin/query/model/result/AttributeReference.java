package aitsi.m3spin.query.model.result;

import aitsi.m3spin.query.model.AttributeEnum;
import aitsi.m3spin.query.model.Reference;
import aitsi.m3spin.query.model.ReferenceType;
import aitsi.m3spin.query.model.Synonym;
import lombok.Getter;

@Getter
public class AttributeReference extends Reference implements SelectedResult {
    private final Synonym synonym;
    private final AttributeEnum attribute;

    public AttributeReference(Synonym synonym, AttributeEnum attribute) {
        super(ReferenceType.fromAttrType(attribute.getAttrType()));
        this.synonym = synonym;
        this.attribute = attribute;
    }

    @Override
    public boolean equalsToSynonym(Synonym synonym) {
        return this.synonym.equals(synonym);
    }
}
