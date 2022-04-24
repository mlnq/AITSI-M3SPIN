package aitsi.m3spin.parser;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.*;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.parser.exception.MissingCharacterException;
import aitsi.m3spin.parser.exception.MissingCodeEntityException;
import aitsi.m3spin.parser.exception.MissingSimpleKeywordException;
import aitsi.m3spin.parser.exception.SimpleParserException;
import aitsi.m3spin.pkb.PkbImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parser {

    private final PkbImpl pkb = new PkbImpl();
    private final CodeScanner codeScanner;

    public Parser(List<String> code) {
        this.codeScanner = new CodeScanner(code);
    }

    public List<Procedure> parse() throws SimpleParserException {
        System.out.println("Parsing SIMPLE code...");// todo dodać logger, żeby nie używać soutów - task na trello dodać
        Procedure rootProc = parseProcedure();
        System.out.println("Parsing completed.");
        return Collections.singletonList(rootProc);

    }
    /*
    * Wzór metody fillPkb(tnode):
    * 1. insertVar(tnode) / insertProc(tnode)
    * 2. setChild(tnode, tnodeChild) / setSibling(tnode, tnodeSibling)
    * 3. Wywołaj fillPkb() dla tego dziecka/rodzeństwa
    * */
    public void fillPkb(List<Procedure> procedures){
        Procedure rootProc = procedures.get(0);
        pkb.setRoot(rootProc);
        fillPkb(rootProc);
    }

    private void fillPkb(Procedure procedure){
        pkb.insertProc(procedure.getName());
        StatementList stmtList = (StatementList) pkb.setChild(procedure, procedure.getStatementList());
        fillPkb(stmtList);
    }

    private void fillPkb(StatementList stmtList) {
        Statement firstStmt = (Statement) pkb.setChild(stmtList, stmtList.getStmtList().get(0));
        fillPkb(firstStmt);

        Statement nextStmt = firstStmt;

        for (int i = 1; i < stmtList.getStmtList().size(); i++) {
            nextStmt = (Statement) pkb.setRightSibling(nextStmt, stmtList.getStmtList().get(i));
            fillPkb(nextStmt);
        }
        //todo zapisać wszystkie sparsowane elementy w PKB za pomocą interfejsu Ast
        //todo wypełnić varTable
        //todo wypełnić procTable
    }

    private void fillPkb(Statement stmt) {
        if (stmt instanceof Assignment) {
            VariableImpl variable = (VariableImpl) pkb.setChild(stmt, ((AssignmentImpl) stmt).getVariable());
            //ast.setRightSibling(variable, )//todo na spokojnie
        } else if (stmt instanceof While) {

        } else if (stmt instanceof If) {
            //todo
        } else if (stmt instanceof Call) {
            //todo
        } else {
            //todo throw new UnsupportedStatementTypeExcepton()
        }
    }

    private Procedure parseProcedure() throws SimpleParserException {
        parseKeyword(EntityType.PROCEDURE);
        String procName = parseName();

        parseStartingBrace();

        Procedure procedure = new ProcedureImpl(procName, parseStmtList());

        //Whole endingBrace
        parseChar('}', false);

        return procedure;
    }

    private void parseStartingBrace() throws MissingCharacterException {
        parseChar('{');
    }

    private void parseEndingBrace() throws MissingCharacterException {
        parseChar('}');
    }

    private void parseChar(char c) throws MissingCharacterException {//todo do codeScannera
        parseChar(c, true);
    }

    private void parseChar(char c, boolean incFlag) throws MissingCharacterException {//todo do codeScannera
        this.codeScanner.skipWhitespaces();
        if (this.codeScanner.hasCurrentChar(c)) {
            if (incFlag) {
                this.codeScanner.incrementPosition();
            }
        } else {
            throw new MissingCharacterException(c, this.codeScanner.getCurrentPosition());
        }
    }

    private String parseName() throws SimpleParserException {//todo do codeScannera
        StringBuilder name = new StringBuilder(String.valueOf(parseLetter()));
        while (codeScanner.hasCurrentChar()) {
            char currentChar = codeScanner.getCurrentChar();
            if (Character.isLetter(currentChar)) {
                name.append(parseLetter());
            } else if (Character.isDigit(currentChar)) {
                name.append(parseDigit());
            } else {
                break;
            }
        }
        return String.valueOf(name);
    }

    private char parseDigit() throws SimpleParserException {//todo do codeScannera
        char digit = this.codeScanner.getCurrentDigit();
        this.codeScanner.incrementPosition();
        return digit;
    }

    private char parseLetter() throws SimpleParserException {//todo do codeScannera
        char letter = this.codeScanner.getCurrentLetter();
        this.codeScanner.incrementPosition();
        return letter;
    }

    private void parseKeyword(EntityType keyword) throws MissingCodeEntityException {
        String keywordStr = codeScanner.getCurrentString(keyword.getETName().length());
        if (!keyword.getETName().equals(keywordStr)) {
            throw new MissingSimpleKeywordException(keyword, codeScanner.getCurrentPosition());
        }
        codeScanner.skipWhitespaces();
    }

    private StatementList parseStmtList() throws SimpleParserException {
        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(parseStmt());

        while (!this.codeScanner.hasCurrentChar('}')) {
            stmtList.add(parseStmt());
            codeScanner.skipWhitespaces();
        }
        return new StatementListImpl(stmtList);
    }

    private Statement parseStmt() throws SimpleParserException {
        codeScanner.skipWhitespaces();
        String firstWord = parseName();

        codeScanner.skipWhitespaces();
        if (this.codeScanner.hasCurrentChar(EntityType.EQUALS.getETName().charAt(0))) {
            this.codeScanner.incrementPosition();
            return parseAssignmentAfterEquals(firstWord);
        } else if (EntityType.IF.getETName().equals(firstWord)) {
            return parseIf();
        } else {
            return parseWhile();
        }
    }

    private If parseIf() {
        return null;//todo w przyszłych iteracjach
    }

    private While parseWhile() throws SimpleParserException {
        String conditionVar = parseName();

        parseStartingBrace();

        StatementList stmtList = parseStmtList();

        parseEndingBrace();
        return new WhileImpl(new VariableImpl(conditionVar), stmtList);
    }

    private Assignment parseAssignmentAfterEquals(String leftSideVar) throws SimpleParserException {
        codeScanner.skipWhitespaces();
        Expression expr = parseExpression();

        parseChar(';');
        return new AssignmentImpl(new VariableImpl(leftSideVar), expr);
    }

    private Expression parseExpression() throws SimpleParserException {

        codeScanner.skipWhitespaces();
        Factor factor = parseFactor();
        codeScanner.skipWhitespaces();

        if (codeScanner.getCurrentChar() == EntityType.PLUS.getETName().charAt(0)) {
            codeScanner.incrementPosition();
            codeScanner.skipWhitespaces();
            return new ExpressionImpl(factor, parseExpression());
        } else {
            return factor;
        }
    }

    private Factor parseFactor() throws SimpleParserException {
        codeScanner.skipWhitespaces();
        Factor factor;
        if (Character.isLetter(this.codeScanner.getCurrentChar())) {
            factor = new VariableImpl(parseName());
        } else {
            factor = new ConstantImpl(parseConst());
        }
        return factor;
    }

    private int parseConst() throws SimpleParserException {
        StringBuilder name = new StringBuilder(String.valueOf(parseDigit()));
        while (codeScanner.hasCurrentChar()) {
            char currentChar = codeScanner.getCurrentChar();
            if (Character.isDigit(currentChar)) {
                name.append(parseDigit());
            } else {
                break;
            }
        }
        return Integer.parseInt(String.valueOf(name));
    }
}
