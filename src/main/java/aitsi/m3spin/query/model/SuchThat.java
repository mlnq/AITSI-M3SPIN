package aitsi.m3spin.query.model;

import lombok.Getter;

@Getter
public class SuchThat implements PqlClause {
    private RelationEnum relation;
    private RelationArgument firstArgument;
    private RelationArgument secondArgument;

    @Override
    public boolean usesSynonym(Synonym synonym) {
        return firstArgument.equals(synonym) || secondArgument.equals(synonym);
    }
}
