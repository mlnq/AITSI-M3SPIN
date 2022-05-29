package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.query.evaluator.clause.SuchThatEvaluator;
import aitsi.m3spin.query.model.enums.RelationshipEnum;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SuchThat implements PqlClause {
    private RelationshipEnum relation;
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
