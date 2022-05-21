package aitsi.m3spin.query.model.result;

import lombok.Data;

@Data
public class BooleanResult extends QueryResult implements SelectedResult {
    private final boolean result;
}
