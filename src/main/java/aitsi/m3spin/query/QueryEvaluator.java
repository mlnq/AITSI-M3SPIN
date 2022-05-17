package aitsi.m3spin.query;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.query.model.Declaration;
import aitsi.m3spin.query.model.Query;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class QueryEvaluator {
    private List<Declaration> declarationList;
    private List<Query> queryList;

    public List<TNode> evaluateQueries() {
        return null;//todo zadanie #5
    }
}
