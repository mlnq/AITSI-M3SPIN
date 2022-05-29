package aitsi.m3spin.query.model.result.reference;

import aitsi.m3spin.query.evaluator.exception.NoSynonymInSelectedResultException;
import aitsi.m3spin.query.model.references.Synonym;

public class SelectedBoolean implements SelectedResult {
    @Override
    public Synonym getSynonym() throws NoSynonymInSelectedResultException {
        throw new NoSynonymInSelectedResultException(this);
    }
}
