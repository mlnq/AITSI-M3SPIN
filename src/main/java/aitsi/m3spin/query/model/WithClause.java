package aitsi.m3spin.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class WithClause {
    private Declaration synonym;
    private AttributeEnum attribute;
    //nazwa zmiennej lub integer
    private RelationArgument value;

    public WithClause(Declaration declaration, AttributeEnum attribute, RelationArgument relationArgument) {

    }
}