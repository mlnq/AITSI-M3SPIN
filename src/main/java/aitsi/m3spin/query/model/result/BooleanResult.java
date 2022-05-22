package aitsi.m3spin.query.model.result;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BooleanResult extends QueryResult implements SelectedResult {
    private final boolean result;
}
