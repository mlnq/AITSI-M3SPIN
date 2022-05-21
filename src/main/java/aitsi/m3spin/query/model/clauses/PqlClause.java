package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisionException;
import aitsi.m3spin.query.model.Synonym;

import java.util.Set;

public interface PqlClause {
    boolean usesSynonym(Synonym synonym);

    boolean evaluate() throws IncompatibleTypesComparisionException;

    Set<TNode> evaluate(Set<TNode> previousResult);
}
