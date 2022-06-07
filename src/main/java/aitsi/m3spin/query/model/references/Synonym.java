package aitsi.m3spin.query.model.references;


import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.query.model.enums.AttributeTypeEnum;
import aitsi.m3spin.query.model.enums.WithArgRefType;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import aitsi.m3spin.query.model.result.reference.SelectedResult;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class Synonym extends ComplexTypeReference
        implements SelectedResult, RelationshipArgumentRef {
    private final String name;
    private final EntityType synonymType;

    public Synonym(String name, EntityType type) {
        super(AttributeTypeEnum.INTEGER, WithArgRefType.PROG_LINE);
        this.name = name;
        this.synonymType = type;
    }

    @Override
    public boolean equalsToSynonym(Synonym synonym) {
        return this.equals(synonym);
    }

    @Override
    public Synonym getSynonym() {
        return this;
    }

    @Override
    public boolean isBooleanSelect() {
        return false;
    }

    @Override
    public ReferenceType getArgRefType() {
        return ReferenceType.SYNONYM;
    }
}
