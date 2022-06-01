package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.query.QueryProcessorException;
import aitsi.m3spin.query.evaluator.clause.SuchThatEvaluator;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;
import aitsi.m3spin.query.model.enums.RelationshipPreprocEnum;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

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

    public RelationshipEvaluatorEnum getEvaluatorRelationship() throws QueryProcessorException {
        return Arrays.stream(RelationshipEvaluatorEnum.values())
                .filter(relationshipEvaluatorEnum -> relationshipEvaluatorEnum.getRelationName()
                        .equals(this.getRelation().getRelationName()))
                .findFirst()
                .orElseThrow(() -> new QueryProcessorException("Failed to map enums."));
    }
}
