package aitsi.m3spin.parser;

import aitsi.m3spin.commons.AssignmentImpl;
import aitsi.m3spin.commons.EntityType;
import aitsi.m3spin.commons.ProcedureImpl;
import aitsi.m3spin.commons.VariableImpl;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.parser.exception.IllegalCharacterException;
import aitsi.m3spin.parser.exception.MissingCharacterException;
import aitsi.m3spin.parser.exception.MissingSimpleKeywordException;
import aitsi.m3spin.parser.exception.SimpleParserException;
import aitsi.m3spin.pkb.AstImpl;
import aitsi.m3spin.pkb.Interfaces.AST;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    //private Dictionary<String, Object> parserDict = new Hashtable<String, Object>();//todo: zapytać co to
    private AST ast = new AstImpl();
    private CodeScanner codeScanner;

    public Parser(List<String> code) {
        this.codeScanner = new CodeScanner(code);
    }

    public void parse() throws SimpleParserException {
        System.out.println("Parsing SIMPLE code...");
        parseProcedure();
        System.out.println("Parsing completed.");
    }

    private void parseProcedure() throws SimpleParserException {
        parseProcedureTag();
        Procedure procedure = new ProcedureImpl(parseName());

        ast.setRoot(procedure);

        parseStartingBrace();
        parseStmtList();
        parseEndingBrace();
    }

    private void parseStartingBrace() throws MissingCharacterException {
        parseChar('{');
    }

    private void parseEndingBrace() throws MissingCharacterException {
        parseChar('}');
    }

    private void parseChar(char c) throws MissingCharacterException {
        this.codeScanner.skipWhitespaces();
        if (this.codeScanner.hasNextChar(c)) {
            this.codeScanner.incrementPosition();
        } else throw new MissingCharacterException(c, this.codeScanner.getCurrentPosition());
    }

    private String parseName() throws IllegalCharacterException {
        StringBuilder name = new StringBuilder(String.valueOf(parseLetter()));
        while (codeScanner.hasNextChar()) {
            char nextChar = codeScanner.getNextChar();
            if (Character.isLetter(nextChar)) {
                name.append(parseLetter());
            } else if (Character.isDigit(nextChar)) {
                name.append(parseDigit());
            } else {
                break;
            }
        }
        return String.valueOf(name);
    }

    private char parseDigit() {
        return 0;//todo analogicznie do parseLetter()
    }

    private char parseLetter() throws IllegalCharacterException {
        char letter = this.codeScanner.getNextLetter();
        this.codeScanner.incrementPosition();
        return letter;
    }

    private void parseProcedureTag() throws MissingSimpleKeywordException {
        String procedureKeyword = codeScanner.getNextString(EntityType.PROCEDURE.getETName().length());
        if (!EntityType.PROCEDURE.getETName().equals(procedureKeyword)) {
            throw new MissingSimpleKeywordException(EntityType.PROCEDURE, codeScanner.getCurrentPosition());
        }
    }

    private List<Statement> parseStmtList() throws SimpleParserException {
        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(parseStmt());

        while (!this.codeScanner.hasNextChar('}')) {
            stmtList.add(parseStmt());
            codeScanner.skipWhitespaces();
        }
        return stmtList;
    }


    private Statement parseStmt() throws SimpleParserException {
        codeScanner.skipWhitespaces();
        String firstWord = parseName();

        codeScanner.skipWhitespaces();
        if (this.codeScanner.hasNextChar('=')) {
            this.codeScanner.incrementPosition();
            return parseAssignmentAfterEquals(firstWord);
        } else {
            return parseWhile();
        }
    }

    private While parseWhile() {
        return null;//todo
    }

    private Assignment parseAssignmentAfterEquals(String leftSideVar) throws MissingCharacterException {
        Expression expr = parseExpression();
        parseChar(';');
        return new AssignmentImpl(new VariableImpl(leftSideVar), expr);
    }

    private Expression parseExpression() {
        return null;//todo
    }
}
