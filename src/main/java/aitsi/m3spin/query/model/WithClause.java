package aitsi.m3spin.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithClause {
    private Declaration synonym;
    private AttributeEnum attribute;
    //nazwa zmiennej lub integer
    private RelationArgument value;
}

/*while w, stmt s, var v;
select w such that Parent(w, s) with v.varName = 'duoawołowa'*/
