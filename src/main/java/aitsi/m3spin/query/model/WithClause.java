package aitsi.m3spin.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithClause implements PqlClause {
    private Reference leftHandReference;
    private Reference rightHandReference;

    @Override
    public boolean usesSynonym(Synonym synonym) {
        return usesSynonym(leftHandReference, synonym) || usesSynonym(rightHandReference, synonym);
    }

    public boolean usesSynonym(Reference reference, Synonym synonym) {
        if (reference instanceof Synonym) {
            return ((Synonym) reference).equals(synonym);
        } else if (reference instanceof AttributeReference) {
            return ((AttributeReference) reference).getSynonym().equals(synonym);
        }
    }
}
