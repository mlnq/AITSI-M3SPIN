package aitsi.m3spin.queryProcessor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithClause {
    private Declaration synonym;
    private AttributeNameEnum attrName;
    //nazwa zmiennej lub integer
    private RelationArgument value;

}
