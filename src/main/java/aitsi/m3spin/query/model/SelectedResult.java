package aitsi.m3spin.query.model;

import aitsi.m3spin.query.evaluator.exception.NoSynonymInSelectedResultException;

public interface SelectedResult {
    static Synonym extractSynonym(SelectedResult selectedResult) throws NoSynonymInSelectedResultException {
        if(selectedResult instanceof Synonym) return (Synonym) selectedResult;
        else if(selectedResult instanceof AttributeReference) return ((AttributeReference) selectedResult).getSynonym();
        else throw new NoSynonymInSelectedResultException(selectedResult);
    }
}
