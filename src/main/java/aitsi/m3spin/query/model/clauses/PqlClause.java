package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisonException;
import aitsi.m3spin.query.model.references.Synonym;

public interface PqlClause {
    boolean usesSynonym(Synonym synonym) throws IncompatibleTypesComparisonException;

    Class<?> getEvaluatorClass();
}
