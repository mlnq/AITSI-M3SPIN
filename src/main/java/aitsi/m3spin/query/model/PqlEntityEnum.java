package aitsi.m3spin.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PqlEntityEnum {
    SELECT("Select"),
    SUCH("such"),
    SUCH_THAT("such that"),
    WITH("with"),
    DECLARATION_DELIMITER(";"),
    SYNONYM_DELIMITER(",");

    private final String name;
}
