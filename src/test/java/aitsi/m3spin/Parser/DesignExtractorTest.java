package aitsi.m3spin.Parser;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.*;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.interfaces.Modifies;
import aitsi.m3spin.spafrontend.extractor.DesignExtractor;
import aitsi.m3spin.spafrontend.parser.exception.UnknownStatementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DesignExtractorTest {
    Pkb pkb;
    final String MAIN = "Main";
    final String FIRST = "FIRST";
    final String X = "x";
    final String Y = "y";
    final String Z = "z";
    final String I = "i";

    @BeforeEach
    void prepareProcedures() throws UnknownStatementType {
        List<Procedure> procedures = new ArrayList<>();
        List<Statement> firstProcStatementsList = new ArrayList<>();
        Variable xVar = new VariableImpl(X);
        Variable yVar = new VariableImpl(Y);
        Variable zVar = new VariableImpl(Z);
        Variable iVar = new VariableImpl(I);
        Variable iVar2 = new VariableImpl(I);
        Variable xVar2 = new VariableImpl(X);
        Variable yVar2 = new VariableImpl(Y);
        Variable zVar2 = new VariableImpl(Z);
        Variable xVar3 = new VariableImpl(X);
        Assignment firstAssignment = new AssignmentImpl(xVar,
                new ExpressionImpl(yVar, new ExpressionImpl(zVar, new ExpressionImpl(iVar))));
        firstProcStatementsList.add(firstAssignment);

        Assignment whileAssigment = new AssignmentImpl(yVar2, new ExpressionImpl(zVar2, new ExpressionImpl(xVar2)));
        WhileImpl whileImp = new WhileImpl(iVar2, new StatementListImpl(Collections.singletonList(whileAssigment)));
        firstProcStatementsList.add(whileImp);

        Procedure firstProcedure = new ProcedureImpl(MAIN, new StatementListImpl(firstProcStatementsList));
        procedures.add(firstProcedure);

        List<Statement> secondProcStatementsList = new ArrayList<>();
        Assignment secondProcAssign = new AssignmentImpl(xVar3, new ExpressionImpl(new ConstantImpl(2)));
        secondProcStatementsList.add(secondProcAssign);
        Procedure secondProcedure = new ProcedureImpl(FIRST, new StatementListImpl(secondProcStatementsList));

        procedures.add(secondProcedure);

        pkb = new Pkb();
        DesignExtractor designExtractor = new DesignExtractor(pkb);
        designExtractor.fillPkb(procedures);
    }

    @Test
    void fillPkbProcedure_SimpleProcedure_Parsed() {
        TNode root = pkb.getAst().getRoot();

        assertEquals(MAIN, root.getAttribute());
        assertEquals(EntityType.PROCEDURE, root.getType());
        assertEquals(FIRST, root.getRightSibling().getAttribute());
        assertEquals(EntityType.PROCEDURE, root.getRightSibling().getType());
    }

    @Test
    void fillPkbWhile_SimpleWhile_Parsed() {
        TNode root = pkb.getAst().getRoot();

        TNode pkbAssigment = root.getChild().getChild();
        assertEquals(EntityType.ASSIGNMENT, pkbAssigment.getType());

        TNode pkbWhile = pkbAssigment.getRightSibling();
        assertEquals(EntityType.WHILE, pkbWhile.getType());

        WhileImpl filledWhile = (WhileImpl) pkbWhile;
        assertEquals(I, filledWhile.getConditionVar().getName());
    }


    @Test
    void fillPkb_SimpleConst_Parsed() {
        TNode secondProcedure = pkb.getAst().getRoot().getRightSibling();

        TNode pkbAssigment = secondProcedure.getChild().getChild();
        assertEquals(EntityType.ASSIGNMENT, pkbAssigment.getType());

        AssignmentImpl filledAssigment = (AssignmentImpl) pkbAssigment;
        assertEquals(X, filledAssigment.getChild().getAttribute());

        TNode pkbExpression = pkbAssigment.getChild().getRightSibling();
        assertEquals(EntityType.EXPRESSION, pkbExpression.getType());
        Expression filledExpression = (Expression) pkbExpression;

        Factor pkbVariable = filledExpression.getFactor();
        assertEquals(EntityType.CONSTANT, pkbVariable.getType());
        Constant filledVariable = (Constant) pkbVariable;
        assertEquals(2, filledVariable.getValue());
    }

    @Test
    void fillPkb_MultiParamAssignment_Filled() {
        TNode root = pkb.getAst().getRoot();

        TNode pkbAssigment = root.getChild().getChild();
        assertEquals(EntityType.ASSIGNMENT, pkbAssigment.getType());

        TNode pkbFirstVariable = pkbAssigment.getChild();
        assertEquals(EntityType.VARIABLE, pkbFirstVariable.getType());
        VariableImpl filledFirstVariable = (VariableImpl) pkbFirstVariable;
        assertEquals(X, filledFirstVariable.getName());

        TNode pkbSecondVariable = pkbAssigment.getChild().getRightSibling().getChild();
        assertEquals(EntityType.VARIABLE, pkbSecondVariable.getType());
        VariableImpl filledSecondVariable = (VariableImpl) pkbSecondVariable;
        assertEquals(Y, filledSecondVariable.getName());

        TNode pkbThirdVariable = filledSecondVariable.getRightSibling().getChild();
        assertEquals(EntityType.VARIABLE, pkbThirdVariable.getType());
        VariableImpl filledThirdVariable = (VariableImpl) pkbThirdVariable;
        assertEquals(Z, filledThirdVariable.getName());

        TNode pkbExpression = pkbThirdVariable.getRightSibling();
        assertEquals(EntityType.EXPRESSION, pkbExpression.getType());
        ExpressionImpl filledExpression = (ExpressionImpl) pkbExpression;

        TNode pkbFourthVariable = filledExpression.getFactor();
        assertEquals(EntityType.VARIABLE, pkbThirdVariable.getType());
        VariableImpl filledFourthVariable = (VariableImpl) pkbFourthVariable;
        assertEquals(I, filledFourthVariable.getName());
    }


    @Test
    void fillPkb_ModifiesByStmFill_InfoAdded() {
        TNode root = pkb.getAst().getRoot();

        TNode pkbFirstAssigment = root.getChild().getChild();
        assertEquals(EntityType.ASSIGNMENT, pkbFirstAssigment.getType());
        AssignmentImpl fillFirstAssigment = (AssignmentImpl) pkbFirstAssigment;

        Modifies modifies = pkb.getModifiesInterface();
        boolean firstIsModified = modifies.isModified((Variable) fillFirstAssigment.getChild(), fillFirstAssigment);
        assertTrue(firstIsModified);

        TNode secondProcedure = pkb.getAst().getRoot().getRightSibling();
        TNode pkbSecondAssigment = secondProcedure.getChild().getChild();
        assertEquals(EntityType.ASSIGNMENT, pkbSecondAssigment.getType());

        AssignmentImpl filledSecondAssigment = (AssignmentImpl) pkbSecondAssigment;
        boolean secondIsModified = modifies.isModified((Variable) filledSecondAssigment.getChild(), filledSecondAssigment);
        assertTrue(secondIsModified);
    }

    @Test
    void fillPkb_ModifiesByProcFill_InfoAdded() {
        TNode root = pkb.getAst().getRoot();

        Modifies modifies = pkb.getModifiesInterface();

        TNode pkbFirstAssigment = root.getChild().getChild();
        assertEquals(EntityType.ASSIGNMENT, pkbFirstAssigment.getType());
        AssignmentImpl fillFirstAssigment = (AssignmentImpl) pkbFirstAssigment;
        boolean firstIsModified = modifies.isModified((Variable) fillFirstAssigment.getChild(), (Procedure) root);
        assertTrue(firstIsModified);

        TNode secondProcedure = pkb.getAst().getRoot().getRightSibling();
        TNode pkbSecondAssigment = secondProcedure.getChild().getChild();
        assertEquals(EntityType.ASSIGNMENT, pkbSecondAssigment.getType());
        AssignmentImpl filledSecondAssigment = (AssignmentImpl) pkbSecondAssigment;
        boolean secondIsModified = modifies.isModified((Variable) filledSecondAssigment.getChild(), (Procedure) secondProcedure);
        assertTrue(secondIsModified);
    }
}
