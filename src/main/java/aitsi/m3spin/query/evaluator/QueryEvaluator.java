package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.model.*;

import javax.print.attribute.standard.PresentationDirection;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueryEvaluator {
    private final Pkb pkb;
    private final NodeDao nodeDao;

    public QueryEvaluator(Pkb pkb){
        this.pkb = pkb;
        this.nodeDao = new NodeDao(pkb);
    }

    public Set<TNode> evaluateQuery(Query query) {

        Set<TNode> foundNodes = evaluateWithList(query.getWithList());
        Declaration select = query.getSelect();
        if (foundNodes.isEmpty()) {
            foundNodes = nodeDao.findAllByType(pkb.getAst().getRoot(), select.getType());
        }
        Set<TNode> suchThatResult = evaluateSuchThatList(query.getSuchThatList(), foundNodes, select);

        return result;
    }

    private Set<TNode> evaluateWithList(List<WithClause> withList) {
        Set<TNode> result = new HashSet<>();
        withList.forEach(withClause -> {
            result.addAll(evaluateWithClause(withClause));
        });
        return result;
    }

    private Set<TNode> evaluateWithClause(WithClause withClause) {
        if (!withClause.getSynonym().getType().equals(withClause.getAttribute().getEntityType()))
            throw new NoSuchAttributeException()
        Set<TNode> nodes = nodeDao.findAllByType(pkb.getAst().getRoot(), withClause.getSynonym().getType());
        nodes.stream()
                .filter(node -> node.getAttribute())
        return nodes;
    }
    //TODO ogarnąć atrybuty - zadanie #20


    private Set<TNode> evaluateSuchThatList(List<SuchThat> suchThatList, Set<TNode> foundNodes, Declaration searched) {
        Set<TNode> result;
        //suchThatList.forEach(suchThat -> {
        result = evaluateSuchThat(suchThatList.get(0));
        //});
        return result;
    }

    private Set<TNode> evaluateSuchThat(SuchThat suchThat) {
        Set<TNode> result = new HashSet<>();
        Set<TNode> firstArgumentNodes = getNodesFor(suchThat.getFirstArgument());
        Set<TNode> secondArgumentNodes = getNodesFor(suchThat.getSecondArgument());

        RelationEnum relation = suchThat.getRelation();
        firstArgumentNodes.forEach(node -> {
            if (suchThat.getFirstArgument() instanceof Declaration)
                findNodesInRelation(node, relation);
        });
        return result;
    }

    private Set<TNode> getNodesFor(RelationArgument relationArgument) {
        Set<TNode> result = new HashSet<>();
        if (relationArgument instanceof Declaration) {
            Declaration declaration = (Declaration) relationArgument;
            result = nodeDao.findAllByType(pkb.getAst().getRoot(), declaration.getType());
        } else if (relationArgument instanceof Constant) {
            Constant constant = (Constant) relationArgument;
            result = nodeDao.findAllConstants(constant.getValue(), pkb.getAst().getRoot());
        } else {
            SimpleEntityName simpleEntityName = (SimpleEntityName) relationArgument;
            result = nodeDao.findAllByAttribute(pkb.getAst().getRoot(), simpleEntityName.getEntityName());
        }
        return result;
    }

    private Set<TNode> findNodesInRelation(TNode node, RelationEnum relation) {
        Set<TNode> result = new HashSet<>();
        switch (relation) {
            case FOLLOWS_T:
                pkb.getFollowsInterface().getFollowsT((Statement) node).forEach(res -> result.add(res));
                break;
            case FOLLOWS:
                result.add(pkb.getFollowsInterface().getFollows((Statement) node));
                break;
            case PARENT_T:
                result = pkb.getParentInterface().getParentT((Statement) node);
                break;
            case PARENT:
                result.add(pkb.getParentInterface().getParent((Statement) node));
                break;
            case USES:
                if (((Declaration) node).getType().equals(EntityType.PROCEDURE)) {
                    result = pkb.getUsesInterface().getVarsUsedByProc((Procedure) node);
                } else {
                    result = pkb.getUsesInterface().getVarsUsedByStmt((Statement) node);
                }
                break;
            case MODIFIES:
                if (node instanceof Procedure) {
                    result = pkb.getModifiesInterface().getModified((Procedure) node);
                } else {
                    result = pkb.getModifiesInterface().getModified((Statement) node);
                }
                break;
        }
        return result;
    }
}
