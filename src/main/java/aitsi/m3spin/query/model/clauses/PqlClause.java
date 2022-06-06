package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.query.model.references.Synonym;

import java.util.Set;

public interface PqlClause {
    boolean usesSynonym(Synonym synonym);

    Class<?> getEvaluatorClass();

    Synonym getOtherUsedSynonym(Synonym excludedSynonym);

    default boolean usesAnySynonym(Set<Synonym> synonyms) {
        return synonyms.stream()
                .anyMatch(this::usesSynonym);
    }
}
