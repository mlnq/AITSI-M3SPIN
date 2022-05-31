package aitsi.m3spin.Parser;
import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.*;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.interfaces.Modifies;
import aitsi.m3spin.spafrontend.parser.Parser;
import aitsi.m3spin.spafrontend.parser.exception.SimpleParserException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ParserTest{

    @Test
    void parseProcedure_SimpleProcedure_Parsed() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("z = x +y;");
        codeLines.add("}");

        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> procedures = new ArrayList<>();
        try {
            procedures = parser.parse();
        } catch (SimpleParserException e) {
            e.printStackTrace();
        }
        Procedure parseProcedure = procedures.get(0);

        assertEquals(parseProcedure.getName(), "Main");
        assertEquals(parseProcedure.getType(), EntityType.PROCEDURE);
    }

    @Test
    void parseStmtList_TwoStmt_Parsed() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("z = x +y;");
        codeLines.add("a = b + c;");
        codeLines.add("}");

        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> procedures = new ArrayList<>();
        try {
            procedures = parser.parse();
        } catch (SimpleParserException e) {
            e.printStackTrace();
        }
        Procedure parseProcedure = procedures.get(0);
        assertEquals(parseProcedure.getStatementList().getStatements().size(),2);
    }

    @Test
    void parseStmt_MuliParamAsigment_Parsed() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("z = x + y + k;");
        codeLines.add("a = b + c;");
        codeLines.add("}");

        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> procedures = new ArrayList<>();
        try {
            procedures = parser.parse();
        } catch (SimpleParserException e) {
            e.printStackTrace();
        }

        AssignmentImpl assignment = (AssignmentImpl) procedures.get(0).getStatementList().getStatements().get(0);

        assertEquals(assignment.getVariable().getName(),"z");
        assertEquals(assignment.getExpression().getFactor().getAttribute(), "x");
        assertEquals(assignment.getExpression().getExpression().getFactor().getAttribute(), "y");
        assertEquals(assignment.getExpression().getExpression().getExpression().getFactor().getAttribute(), "k");
    }

    @Test
    void parseWhile_SimpleWhile_Parsed() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("while i {");
        codeLines.add("a = b + c;}");
        codeLines.add("}");

        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> procedures = new ArrayList<>();
        try {
            procedures = parser.parse();
        } catch (SimpleParserException e) {
            e.printStackTrace();
        }

        WhileImpl parseWhile = (WhileImpl) procedures.get(0).getStatementList().getStatements().get(0);
        assertEquals(parseWhile.getType(), EntityType.WHILE);
        assertEquals(parseWhile.getConditionVar().getName(), "i");
    }


    @Test
    void parseConst_SimpleConst_Parsed() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("a = 2;");
        codeLines.add("}");

        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> procedures = new ArrayList<>();
        try {
            procedures = parser.parse();
        } catch (SimpleParserException e) {
            e.printStackTrace();
        }
        AssignmentImpl parseAssignment = (AssignmentImpl) procedures.get(0).getStatementList().getStatements().get(0);
        assertEquals(parseAssignment.getVariable().getName(),"a");
        ConstantImpl parseConstant = (ConstantImpl) parseAssignment.getExpression().getFactor();
        assertEquals(parseConstant.getValue(), 2);
    }

    @Test
    void fillPkbProcedure_SimpleProcedure_Parsed() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("z = x +y;");
        codeLines.add("}");

        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> procedures;
        try {
            procedures = parser.parse();
            parser.fillPkb(procedures);
        } catch (SimpleParserException e) {
            e.printStackTrace();
        }

        assertEquals(pkb.getAst().getRoot().getAttribute(), "Main");
        assertEquals(pkb.getAst().getRoot().getType(), EntityType.PROCEDURE);
    }

    @Test
    void fillPkbWhile_SimpleWhile_Parsed() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("while i {");
        codeLines.add("a = b + c;}");
        codeLines.add("x = z + k;");
        codeLines.add("}");

        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> procedures;
        try {
            procedures = parser.parse();
            parser.fillPkb(procedures);
        } catch (SimpleParserException e) {
            e.printStackTrace();
        }
        TNode root = pkb.getAst().getRoot();
        WhileImpl pkbWhile = (WhileImpl) root.getChild().getChild();
        assertEquals(pkbWhile.getType(), EntityType.WHILE);
        assertEquals(pkbWhile.getConditionVar().getName(), "i");
    }


    @Test
    void fillPkb_SimpleConst_Parsed() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("a = 2;");
        codeLines.add("}");

        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> procedures;
        try {
            procedures = parser.parse();
            parser.fillPkb(procedures);
        } catch (SimpleParserException e) {
            e.printStackTrace();
        }
        TNode root = pkb.getAst().getRoot();
        AssignmentImpl pkbAssigment = (AssignmentImpl) root.getChild().getChild();
        ExpressionImpl parseExpression = (ExpressionImpl) pkbAssigment.getChild().getRightSibling();
        assertEquals(pkbAssigment.getType(), EntityType.ASSIGNMENT);
        assertEquals(pkbAssigment.getChild().getAttribute(), "a");
        ConstantImpl pkbConstant = (ConstantImpl) parseExpression.getFactor();
        assertEquals(pkbConstant.getType(), EntityType.CONSTANT);
        assertEquals(pkbConstant.getValue(), 2);
    }

    @Test
    void fillPkb_MuliParamAsigment_Filled() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("z = x + y + k;");
        codeLines.add("}");

        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> procedures;
        try {
            procedures = parser.parse();
            parser.fillPkb(procedures);
        } catch (SimpleParserException e) {
            e.printStackTrace();
        }
        TNode root = pkb.getAst().getRoot();

        VariableImpl firstVariable = (VariableImpl) root.getChild().getChild().getChild();
        assertEquals(firstVariable.getAttribute(), "z");
        assertEquals(firstVariable.getType(), EntityType.VARIABLE);

        VariableImpl secondVariable = (VariableImpl) firstVariable.getRightSibling().getChild();
        assertEquals(secondVariable.getAttribute(), "x");
        assertEquals(secondVariable.getType(), EntityType.VARIABLE);

        VariableImpl thirdVariable = (VariableImpl) secondVariable.getRightSibling().getChild();
        assertEquals(thirdVariable.getAttribute(), "y");
        assertEquals(thirdVariable.getType(), EntityType.VARIABLE);

        Expression fourthExpression = (Expression) thirdVariable.getRightSibling();
        assertEquals(fourthExpression.getFactor().getAttribute(), "k");
        assertEquals(fourthExpression.getFactor().getType(), EntityType.VARIABLE);
    }


    @Test
    void fillPkb_ModifiesByStmFill_InfoAdded() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("a = b + c;");
        codeLines.add("c = 2;");
        codeLines.add("d = c;");
        codeLines.add("}");

        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> procedures;
        try {
            procedures = parser.parse();
            parser.fillPkb(procedures);
        } catch (SimpleParserException e) {
            e.printStackTrace();
        }

        TNode root = pkb.getAst().getRoot();
        Assignment firstAssigment = (Assignment)  root.getChild().getChild();
        Modifies modifies = pkb.getModifiesInterface();
        boolean firstIsModified = modifies.isModified((Variable) firstAssigment.getChild(), firstAssigment);
        assertTrue(firstIsModified);

        Assignment secondAssigment = (Assignment) firstAssigment.getRightSibling();
        boolean secondIsModified = modifies.isModified((Variable) secondAssigment.getChild(), secondAssigment);
        assertTrue(secondIsModified);

        Assignment thirdAssigment = (Assignment) secondAssigment.getRightSibling();
        boolean thirdIsModified = modifies.isModified((Variable) thirdAssigment.getChild(), thirdAssigment);
        assertTrue(thirdIsModified);
    }

    @Test
    void fillPkb_ModifiesByProcFill_InfoAdded() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("a = b + c;");
        codeLines.add("c = 2;");
        codeLines.add("d = c;");
        codeLines.add("}");

        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> procedures;
        try {
            procedures = parser.parse();
            parser.fillPkb(procedures);
        } catch (SimpleParserException e) {
            e.printStackTrace();
        }

        TNode root = pkb.getAst().getRoot();

        Modifies modifies = pkb.getModifiesInterface();
        Variable firstVariable = (Variable) root.getChild().getChild().getChild();
        boolean firstIsModified = modifies.isModified(firstVariable, (Procedure) root);
        assertTrue(firstIsModified);

        Variable secondVariable = (Variable) firstVariable.getParent().getRightSibling().getChild();
        boolean secondIsModified = modifies.isModified(secondVariable, (Procedure) root);
        assertTrue(secondIsModified);

        Variable thirdVariable = (Variable) secondVariable.getParent().getRightSibling().getChild();
        boolean thirdIsModified = modifies.isModified(thirdVariable, (Procedure) root);
        assertTrue(thirdIsModified);
    }
}
