package aitsi.m3spin.query.model.result;

import aitsi.m3spin.query.evaluator.exception.NoSynonymInSelectedResultException;
import aitsi.m3spin.query.model.Synonym;

public interface SelectedResult {
    static Synonym extractSynonym(SelectedResult selectedResult) throws NoSynonymInSelectedResultException {
        if(selectedResult instanceof Synonym) return (Synonym) selectedResult;
        else if(selectedResult instanceof AttributeReference) return ((AttributeReference) selectedResult).getSynonym();
        else throw new NoSynonymInSelectedResultException(selectedResult);
    }
}
