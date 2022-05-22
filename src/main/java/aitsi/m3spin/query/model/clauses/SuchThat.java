package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.query.evaluator.clause.SuchThatEvaluator;
import aitsi.m3spin.query.model.RelationEnum;
import aitsi.m3spin.query.model.Synonym;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import lombok.Getter;

@Getter
public class SuchThat implements PqlClause {
    private RelationEnum relation;
    private RelationshipArgumentRef firstArgument;//todo relation argument zrobić jak w handbooku
    private RelationshipArgumentRef secondArgument;

    @Override
    public boolean usesSynonym(Synonym synonym) {
        return firstArgument.equals(synonym) || secondArgument.equals(synonym);//todo - może zaimplementować usesSynonym() w klasach dziedziczących z RelationshipArgumentRef?
    }

    @Override
    public Class<SuchThatEvaluator> getEvaluatorClass() {
        return SuchThatEvaluator.class;
    }
}
