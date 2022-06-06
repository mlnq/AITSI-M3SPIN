package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.*;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.model.StringAttribute;
import aitsi.m3spin.query.model.clauses.SuchThat;
import aitsi.m3spin.query.model.enums.RelationshipPreprocEnum;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.references.WildcardReference;
import aitsi.m3spin.query.model.result.actual.TNodeSetResult;
import aitsi.m3spin.query.model.result.reference.SelectedResult;

import java.util.Collections;

public abstract class QueryTestingData {
    protected static final String PROCEDURE_NAME = "Main";
    private static final String VAR_NAME = "x";
    protected SelectedResult selectedProcSynonym;
    protected Synonym procSynonym;
    protected TNodeSetResult assignResult;
    protected TNodeSetResult whileResult;
    protected TNodeSetResult procedureResult;
    protected Pkb pkb;

    protected SuchThat followsSuchThat;

    protected Procedure procedure = new ProcedureImpl(null, 1, new StringAttribute(PROCEDURE_NAME));
    protected While whileStmt = new WhileImpl();
    protected Synonym whileSynonym;
    protected Synonym assignSynonym;
    protected Synonym varSynonym;
    protected Variable variable = new VariableImpl(VAR_NAME);
    protected Assignment assignment;

    protected void setUp() {
        //SPA-Frontend
        assignment = new AssignmentImpl(variable, new ExpressionImpl());

        pkb = new Pkb();
        TNode returnedProc = pkb.getAst().setRoot(procedure);
        TNode addedWhile = pkb.getAst().setChild(returnedProc, whileStmt);
        pkb.getAst().setSibling(addedWhile, assignment);

        procSynonym = new Synonym("p", EntityType.PROCEDURE);
        whileSynonym = new Synonym("w", EntityType.WHILE);
        assignSynonym = new Synonym("a", EntityType.ASSIGNMENT);
        varSynonym = new Synonym("v", EntityType.VARIABLE);
        selectedProcSynonym = procSynonym;

        followsSuchThat = new SuchThat(RelationshipPreprocEnum.FOLLOWS, whileSynonym, new WildcardReference());

        procedureResult = new TNodeSetResult(Collections.singleton(procedure));
        assignResult = new TNodeSetResult(Collections.singleton(assignment));
        whileResult = new TNodeSetResult(Collections.singleton(whileStmt));
    }
}
