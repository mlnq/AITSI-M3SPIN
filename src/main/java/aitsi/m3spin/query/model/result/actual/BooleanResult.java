package aitsi.m3spin.query.model.result.actual;

import aitsi.m3spin.query.evaluator.exception.NoSynonymInSelectedResultException;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.result.reference.SelectedResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BooleanResult extends QueryResult {
    private final boolean result;

    public boolean get() {
        return result;
    }
}
