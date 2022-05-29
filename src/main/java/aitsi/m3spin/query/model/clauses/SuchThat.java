package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.query.evaluator.clause.SuchThatEvaluator;
import aitsi.m3spin.query.model.enums.RelationEnum;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import lombok.Getter;

@Getter
public class SuchThat implements PqlClause {
    private RelationEnum relation;
    private RelationshipArgumentRef firstArgument;//todo relation argument zrobić jak w handbooku w preprocessorze
    private RelationshipArgumentRef secondArgument;

    @Override
    public boolean usesSynonym(Synonym synonym) {
        return firstArgument.equals(synonym) || secondArgument.equals(synonym);
    }

    @Override
    public Class<SuchThatEvaluator> getEvaluatorClass() {
        return SuchThatEvaluator.class;
    }
}
