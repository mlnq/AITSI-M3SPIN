package aitsi.m3spin.queryProcessor.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Query {

//    stmt s1, s2; assign a; while w;
//Select s1 such that Follows (s1, s2)
//Select a such that Follows* (20, a)
//Select a such that Modifies (a, “x”)
//Select s1 such that Follows (s1, s2) with s2.stmt#= 5
//Select a such that Follows* (20, a) with a.stmt#= 10
//Select a such that Modifies (a,v) with v.varName=”x”

//General form:

//Select x [ such that R (x, y) ] [ with synonym.attrName= value ]

//where R is Follows, Follow*, Parent, Parent*, Modifies, or Uses,
//and value is either variable name or integer.

    //List<Declaration> Select; <-- 4 iteracja jak ktos nie ma zycia x.d.
    private Declaration Select;
    private List<SuchThat> suchThatList;
    private List<WithClause> withList;

    public Query(Declaration select) {
        this.select = select;
    }
}
