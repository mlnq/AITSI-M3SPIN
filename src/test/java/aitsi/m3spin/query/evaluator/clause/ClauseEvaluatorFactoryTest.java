package aitsi.m3spin.query.evaluator.clause;


import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.ClauseEvaluationException;
import aitsi.m3spin.query.model.clauses.SuchThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClauseEvaluatorFactoryTest {
    private final Pkb pkb = new Pkb();
    private final SuchThat suchThat = new SuchThat();
    private TNodeDao tNodeDao;
    private SuchThatEvaluator suchThatEvaluator;

    @BeforeEach
    void setUp() {
        tNodeDao = new TNodeDao(pkb);
        suchThatEvaluator = new SuchThatEvaluator(pkb, tNodeDao, suchThat);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void forClause_SuchThat_createdSuchThatEvaluator() throws ClauseEvaluationException {
        ClauseEvaluatorFactory factory = new ClauseEvaluatorFactory(pkb, tNodeDao);

        ClauseEvaluator evaluatorFromFactory = factory.forClause(suchThat);

        assertEquals(suchThatEvaluator, evaluatorFromFactory);
    }
}
