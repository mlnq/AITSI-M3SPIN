package aitsi.m3spin.parser;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.*;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.parser.exception.MissingCharacterException;
import aitsi.m3spin.parser.exception.MissingCodeEntityException;
import aitsi.m3spin.parser.exception.MissingSimpleKeywordException;
import aitsi.m3spin.parser.exception.SimpleParserException;
import aitsi.m3spin.pkb.AstImpl;
import aitsi.m3spin.pkb.Interfaces.AST;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private AST ast = new AstImpl();
    private CodeScanner codeScanner;

    public Parser(List<String> code) {
        this.codeScanner = new CodeScanner(code);
    }

    public void parse() throws SimpleParserException {
        System.out.println("Parsing SIMPLE code...");
        Procedure rootProc = parseProcedure();
        System.out.println("Parsing completed.");

        ast.setRoot(rootProc);
        //todo zapisać wszystkie sparsowane elementy w PKB za pomocą interfejsu AST
        //todo wypełnić varTable
        //todo wypełnić procTable
    }

    private Procedure parseProcedure() throws SimpleParserException {
        parseKeyword(EntityType.PROCEDURE);
        String procName = parseName();

        parseStartingBrace();

        Procedure procedure = new ProcedureImpl(procName, parseStmtList());

        //Whole endingBrace
        parseChar('}',false);

        return procedure;
    }

    private void parseStartingBrace() throws MissingCharacterException {
        parseChar('{');
    }

    private void parseEndingBrace() throws MissingCharacterException {
        parseChar('}');
    }

    private void parseChar(char c) throws MissingCharacterException {
        parseChar(c,true);
    }
    private void parseChar(char c,boolean incFlag) throws MissingCharacterException {
        this.codeScanner.skipWhitespaces();
        if (this.codeScanner.hasCurrentChar(c)) {
            if(incFlag) this.codeScanner.incrementPosition();
        } else throw new MissingCharacterException(c, this.codeScanner.getCurrentPosition());
    }

    private String parseName() throws SimpleParserException {
        StringBuilder name = new StringBuilder(String.valueOf(parseLetter()));
        while (codeScanner.hasCurrentChar()) {
            char currentChar = codeScanner.getCurrentChar();
            if (Character.isLetter(currentChar)) {
                name.append(parseLetter());
            } else if (Character.isDigit(currentChar)) {
                name.append(parseDigit());
            }
            else {
                break;
            }
        }
        return String.valueOf(name);
    }

    private char parseDigit() throws SimpleParserException {
        char digit = this.codeScanner.getCurrentDigit();
        this.codeScanner.incrementPosition();
        return digit;
    }

    private char parseLetter() throws SimpleParserException {
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

    private List<Statement> parseStmtList() throws SimpleParserException {
        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(parseStmt());

        while (!this.codeScanner.hasCurrentChar('}')) {
            stmtList.add(parseStmt());
            codeScanner.skipWhitespaces();
        }
        return stmtList;
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
//        parseKeyword(EntityType.WHILE);
        String conditionVar = parseName();

        parseStartingBrace();

        List<Statement> stmtList = parseStmtList();

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

        if(codeScanner.getCurrentChar() == EntityType.PLUS.getETName().charAt(0))
        {
            codeScanner.incrementPosition();
            codeScanner.skipWhitespaces();
            return new ExpressionImpl(factor,parseExpression());
        }
        else return factor;
        /*todo zaimplementować parsowanie wyrażeń wg gramatyki Jarząbka:

        expr : expr ‘+’ factor | factor
        factor : var_name | const_value
        var_name : NAME (zaimplementowane jako parseName())
        const_value : INTEGER

        */
    }

    private Factor parseFactor() throws SimpleParserException {
        //todo rozpoznac czy to constant czy vaname
// zmienna
        codeScanner.skipWhitespaces();
        Factor factor;
        if(Character.isLetter(this.codeScanner.getCurrentChar()))
        {
            //this.codeScanner.incrementPosition();
            factor = new VariableImpl(parseName());
        }
        else factor = new ConstantImpl(parseConst());

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
