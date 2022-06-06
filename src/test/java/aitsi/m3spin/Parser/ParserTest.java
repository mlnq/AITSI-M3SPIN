package aitsi.m3spin.Parser;
import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.*;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.spafrontend.parser.Parser;
import aitsi.m3spin.spafrontend.parser.exception.SimpleParserException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ParserTest{
    static List<String> codeLines;

    @BeforeAll
    static void prepareCodeLines(){
        codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("z = x + y + k;");
        codeLines.add("a = b + c;");
        codeLines.add("while i {");
        codeLines.add("a = b + c;}");
        codeLines.add("}");
        codeLines.add(("procedure Second{"));
        codeLines.add("x = 2;");
        codeLines.add("}");
    }

    @Test
    void parseProcedure_SimpleProcedure_Parsed() throws SimpleParserException {
        Parser parser = new Parser(codeLines);
        List<Procedure> procedures;
        procedures = parser.parse();

        Procedure parseProcedure = procedures.get(0);
        assertEquals("Main", parseProcedure.getName());
        assertEquals(EntityType.PROCEDURE, parseProcedure.getType());
        assertEquals(2, procedures.size());
    }

    @Test
    void parseStmtList_TwoStmt_Parsed() throws SimpleParserException {
        Parser parser = new Parser(codeLines);
        List<Procedure> procedures;
        procedures = parser.parse();

        Procedure firstProcedure = procedures.get(0);
        assertEquals(3, firstProcedure.getStatementList().getStatements().size());

        Procedure secondProcedure = procedures.get(1);
        assertEquals(1, secondProcedure.getStatementList().getStatements().size());
    }

    @Test
    void parseStmt_MuliParamAsigment_Parsed() throws SimpleParserException {
        Parser parser = new Parser(codeLines);
        List<Procedure> procedures;
        procedures = parser.parse();

        Statement parseAssigment = procedures.get(0).getStatementList().getStatements().get(0);
        assertEquals(EntityType.ASSIGNMENT, parseAssigment.getType());

        AssignmentImpl assignment = (AssignmentImpl) parseAssigment;
        assertEquals("z", assignment.getVariable().getName());

        Factor secondFactor = assignment.getExpression().getFactor();
        assertEquals("x", secondFactor.getAttribute());

        Factor thirdFactor = assignment.getExpression().getExpression().getFactor();
        assertEquals("y", thirdFactor.getAttribute());

        Factor fourFactor = assignment.getExpression().getExpression().getExpression().getFactor();
        assertEquals("k", fourFactor.getAttribute());
    }

    @Test
    void parseWhile_SimpleWhile_Parsed() throws SimpleParserException {
        Parser parser = new Parser(codeLines);
        List<Procedure> procedures;
        procedures = parser.parse();

        Statement parseWhile = procedures.get(0).getStatementList().getStatements().get(2);
        assertEquals(EntityType.WHILE, parseWhile.getType());


        WhileImpl whileImpl = (WhileImpl) parseWhile;
        assertEquals("i", whileImpl.getConditionVar().getName());
    }


    @Test
    void parseConst_SimpleConst_Parsed() throws SimpleParserException {
        Parser parser = new Parser(codeLines);
        List<Procedure> procedures;
        procedures = parser.parse();

        Statement parseAssigment = procedures.get(1).getStatementList().getStatements().get(0);
        assertEquals(EntityType.ASSIGNMENT, parseAssigment.getType());

        AssignmentImpl assignment = (AssignmentImpl) parseAssigment;
        assertEquals("x", assignment.getVariable().getName());

        Factor parsedConstant = ((AssignmentImpl) parseAssigment).getExpression().getFactor();
        assertEquals(EntityType.CONSTANT, parsedConstant.getType());

        ConstantImpl firstConstant = (ConstantImpl) parsedConstant;
        assertEquals(2, firstConstant.getValue());
    }
}
