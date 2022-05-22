package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.ClauseEvaluationException;
import aitsi.m3spin.query.model.clauses.PqlClause;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;

@RequiredArgsConstructor
public class ClauseEvaluatorFactory {
    private final Pkb pkb;
    private final TNodeDao tNodeDao;

    public ClauseEvaluator forClause(PqlClause pqlClause) throws ClauseEvaluationException {
        try {
            // 1 sposób - ten chuba lepszy, bo określamy jakie argumenty ma mieć konstruktor, a nie pierwszy lepszy
            Class<?>[] constructorArgsTypes = {Pkb.class, TNodeDao.class, PqlClause.class};
            Constructor<ClauseEvaluator> constructor = (Constructor<ClauseEvaluator>) pqlClause.getEvaluatorClass().getConstructor(constructorArgsTypes);

            // 2 sposób
//            Constructor<ClauseEvaluator> constructor = (Constructor<ClauseEvaluator>) pqlClause.getEvaluatorClass().getConstructors()[0];

            return constructor.newInstance(this.pkb, this.tNodeDao, pqlClause);
        } catch (ReflectiveOperationException e) {
            throw new ClauseEvaluationException(pqlClause);
        }
    }
}
