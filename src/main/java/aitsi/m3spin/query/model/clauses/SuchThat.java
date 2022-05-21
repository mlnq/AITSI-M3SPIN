package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisionException;
import aitsi.m3spin.query.model.RelationArgument;
import aitsi.m3spin.query.model.RelationEnum;
import aitsi.m3spin.query.model.Synonym;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class SuchThat implements PqlClause {
    private RelationEnum relation;
    private RelationArgument firstArgument;//todo relation argument zrobić jak w handbooku
    private RelationArgument secondArgument;

    @Override
    public boolean usesSynonym(Synonym synonym) {
        return firstArgument.equals(synonym) || secondArgument.equals(synonym);//todo
    }
}
