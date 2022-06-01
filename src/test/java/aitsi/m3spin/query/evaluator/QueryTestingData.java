package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.AssignmentImpl;
import aitsi.m3spin.commons.impl.ProcedureImpl;
import aitsi.m3spin.commons.impl.WhileImpl;
import aitsi.m3spin.commons.interfaces.Assignment;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.While;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.model.StringAttribute;
import aitsi.m3spin.query.model.clauses.SuchThat;
import aitsi.m3spin.query.model.enums.RelationshipEnum;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.references.WildcardReference;
import aitsi.m3spin.query.model.result.actual.QueryResult;
import aitsi.m3spin.query.model.result.reference.SelectedResult;

import java.util.Collections;

public abstract class QueryTestingData {
    protected static final String PROCEDURE_NAME = "Main";
    protected SelectedResult selectedResult;
    protected Synonym procSynonym;
    protected QueryResult assignResult;
    protected QueryResult procedureResult;
    protected Pkb pkb;

    protected SuchThat suchThat;

    protected Procedure procedure = new ProcedureImpl(null, 1, new StringAttribute(PROCEDURE_NAME));
    protected Assignment assignment = new AssignmentImpl();
    protected While whileStmt = new WhileImpl();
    protected Synonym whileSynonym;
    protected Synonym assignSynonym;

    //    @BeforeEach
    protected void setUp() {
        pkb = new Pkb();
        TNode returnedProc = pkb.getAst().setRoot(procedure);
        TNode addedWhile = pkb.getAst().setChild(returnedProc, whileStmt);
        pkb.getAst().setSibling(addedWhile, assignment);


        procSynonym = new Synonym("p", EntityType.PROCEDURE);
        whileSynonym = new Synonym("w", EntityType.WHILE);
        assignSynonym = new Synonym("a", EntityType.ASSIGNMENT);
        selectedResult = procSynonym;

        suchThat = new SuchThat(RelationshipEnum.FOLLOWS, whileSynonym, new WildcardReference());

        procedureResult = QueryResult.ofTNodeSet(Collections.singleton(procedure));
        assignResult = QueryResult.ofTNodeSet(Collections.singleton(assignment));
    }
}
