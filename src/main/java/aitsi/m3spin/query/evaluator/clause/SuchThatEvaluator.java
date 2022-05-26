package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.model.Constant;
import aitsi.m3spin.query.model.SimpleEntityName;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import aitsi.m3spin.query.model.result.QueryResult;
import aitsi.m3spin.query.model.result.SelectedResult;
import aitsi.m3spin.query.model.result.TNodeSetResult;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
public class SuchThatEvaluator extends ClauseEvaluator {
    public SuchThatEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
    }


    @Override
    public QueryResult evaluateClause(TNodeSetResult previousResult, SelectedResult selectedResult) {
//        Set<TNode> result = new HashSet<>();
//        Set<TNode> firstArgumentNodes = getNodesFor(suchThat.getFirstArgument());
//        Set<TNode> secondArgumentNodes = getNodesFor(suchThat.getSecondArgument());
//
//        RelationEnum relation = suchThat.getRelation();
//        firstArgumentNodes.forEach(node -> {
//            if (suchThat.getFirstArgument() instanceof Synonym)
//                findNodesInRelation(node, relation);
//        });
//        return result; //todo
        return new TNodeSetResult(Collections.emptySet());
    }

    private Set<TNode> getNodesFor(RelationshipArgumentRef relationshipArgumentRef) {
        Set<TNode> result;
        if (relationshipArgumentRef instanceof Synonym) {
            Synonym synonym = (Synonym) relationshipArgumentRef;
            result = tNodeDao.findAllByType(pkb.getAst().getRoot(), synonym.getSynonymType());
        } else if (relationshipArgumentRef instanceof Constant) {
            Constant constant = (Constant) relationshipArgumentRef;
            result = tNodeDao.findAllConstants(constant.getValue(), pkb.getAst().getRoot());
        } else {
            SimpleEntityName simpleEntityName = (SimpleEntityName) relationshipArgumentRef;
            result = tNodeDao.findAllByAttribute(pkb.getAst().getRoot(), simpleEntityName.getEntityName());
        }
        return result;
    }
}
