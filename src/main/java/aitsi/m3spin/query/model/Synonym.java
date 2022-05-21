package aitsi.m3spin.query.model;


import aitsi.m3spin.commons.enums.EntityType;
import lombok.Getter;

//@Getter
//@RequiredArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode
@Getter
public class Synonym extends Reference {
    private final String name;
    private final EntityType type;

    public Synonym(String name, EntityType type) {
        super(ReferenceType.SYNONYM);
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean equalsToSynonym(Synonym synonym) {
        return this.equals(synonym);
    }
}
