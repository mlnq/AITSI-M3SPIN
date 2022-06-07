package aitsi.m3spin.query.model.result.reference;

import aitsi.m3spin.query.evaluator.exception.NoSynonymInSelectedResultException;
import aitsi.m3spin.query.model.references.Synonym;

public interface SelectedResult {
    Synonym getSynonym() throws NoSynonymInSelectedResultException;

    boolean isBooleanSelect();
}
