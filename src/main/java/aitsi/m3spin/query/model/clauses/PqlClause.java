package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.query.model.Synonym;

public interface PqlClause {
    boolean usesSynonym(Synonym synonym);

    Class<?> getEvaluatorClass();
}
