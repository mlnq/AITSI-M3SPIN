package aitsi.m3spin.query.model.references;


import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.query.model.result.SelectedResult;
import lombok.Getter;

//@Getter
//@RequiredArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode
@Getter
public class Synonym extends ComplexTypeReference implements SelectedResult {
    private final String name;
    private final EntityType synonymType;

    public Synonym(String name, EntityType type) {
        super(ReferenceType.SYNONYM);
        this.name = name;
        this.synonymType = type;
    }

    @Override
    public boolean equalsToSynonym(Synonym synonym) {
        return this.equals(synonym);
    }

    @Override
    public boolean isConstantValue() {
        return false;
    }

    @Override
    public Synonym getSynonym() {
        return this;
    }
}
