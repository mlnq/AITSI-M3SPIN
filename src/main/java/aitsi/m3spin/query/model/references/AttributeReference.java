package aitsi.m3spin.query.model.references;

import aitsi.m3spin.query.model.enums.AttributeEnum;
import aitsi.m3spin.query.model.enums.WithArgRefType;
import aitsi.m3spin.query.model.result.reference.SelectedResult;
import lombok.Getter;

@Getter
public class AttributeReference extends ComplexTypeReference implements SelectedResult {
    private final Synonym synonym;
    private final AttributeEnum attribute;

    public AttributeReference(Synonym synonym, AttributeEnum attribute) {
        super(attribute.getAttrType(), WithArgRefType.ATTR_REF);
        this.synonym = synonym;
        this.attribute = attribute;
    }

    @Override
    public boolean equalsToSynonym(Synonym synonym) {
        return this.synonym.equals(synonym);
    }

}
