package aitsi.m3spin.query.evaluator.exception;

import aitsi.m3spin.query.model.result.reference.SelectedResult;

public class NoSynonymInSelectedResultException extends QueryEvaluatorException {
    public NoSynonymInSelectedResultException(SelectedResult selectedResult) {
        super(String.format("Could not extract a synonym from Select clause result %s", selectedResult));
    }
}
