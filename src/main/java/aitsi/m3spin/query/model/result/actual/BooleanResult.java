package aitsi.m3spin.query.model.result.actual;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BooleanResult extends QueryResult {
    private final boolean result;

    public boolean get() {
        return result;
    }

    @Override
    public boolean isTrue() {
        return this.result;
    }
}
