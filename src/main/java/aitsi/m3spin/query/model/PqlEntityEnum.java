package aitsi.m3spin.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PqlEntityEnum {
    SELECT("Select"),
    BOOLEAN("BOOLEAN"),
    SUCH_THAT("such that"),
    WITH("with"),
    DECLARATION_DELIMITER(";"),
    SYNONYM_DELIMITER(","),
    AND("and");

    private final String name;
}
