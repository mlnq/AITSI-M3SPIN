package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import sun.security.krb5.internal.crypto.EType;

import java.util.Set;

@RequiredArgsConstructor
public class NodeFinderByType extends AbstractNodeFinder {
    private final EntityType type;

//    public NodeFinderByType(EntityType type){
//        super();
//        this.type = type;
//    }

    public static Set<TNode> find(TNode startingNode, Pkb pkb, EntityType type){
        return new NodeFinderByType(type).findAllBy(startingNode, pkb);
    }

    @Override
    protected boolean checkSearchCriteria(final TNode startingNode) {
        return startingNode.getType().equals(type);
    }
}
