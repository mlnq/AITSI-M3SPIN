package aitsi.m3spin.query.model.result;

import aitsi.m3spin.query.evaluator.exception.NoSynonymInSelectedResultException;
import aitsi.m3spin.query.model.references.Synonym;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BooleanResult extends QueryResult implements SelectedResult {
    private final boolean result;

    public boolean get() {
        return result;
    }

    @Override
    public Synonym getSynonym() throws NoSynonymInSelectedResultException {
        throw new NoSynonymInSelectedResultException(this);
    }
}
