package aitsi.m3spin.spafrontend.parser;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.*;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.spafrontend.parser.exception.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class Parser {
    @Getter
    private final Pkb pkb;
    private final CodeScanner codeScanner;
    private Integer counter;

    public Parser(List<String> code, Pkb pkb) {
        this.codeScanner = new CodeScanner(code);
        this.pkb = pkb;
        this.counter = 0;
    }

    public List<Procedure> parse() throws SimpleParserException {
        System.out.println("Parsing SIMPLE code...");
        Procedure rootProc = parseProcedure();
        System.out.println("Parsing completed.");
        return Collections.singletonList(rootProc);
    }

    /*
     * Wzór metody fillPkb(tnode):
     * 1. insertVar(tnode) / insertProc(tnode)
     * 2. setChild(tnode, tnodeChild) / setSibling(tnode, tnodeSibling)
     * 3. Wywołaj fillPkb() dla tego dziecka/rodzeństwa
     *
     *  todo w przyszłuch iteracjach: Calls
     * */
    public void fillPkb(List<Procedure> procedures) throws UnknownStatementType {
        System.out.println("Filling PKB with data...");
        Procedure rootProc = procedures.get(0);
        rootProc = (Procedure) pkb.getAst().setRoot(rootProc);
        fillPkb(rootProc);
        System.out.println("Filling PKB with data completed.");
    }

    private void fillPkb(Procedure procedure) throws UnknownStatementType {
        pkb.getProcTable().insertProc(procedure.getName());
        StatementList stmtList = (StatementList) pkb.getAst().setChild(procedure, procedure.getStatementList());
        RelationshipsInfo relationshipsInfo = fillPkb(stmtList);

        relationshipsInfo.getModifiedVariables()
                .forEach(modifiedVar -> pkb.getModifiesInterface().setModifies(procedure, modifiedVar));

        relationshipsInfo.getUsedVariables()
                .forEach(usedVar -> pkb.getUsesInterface().setUses(procedure, usedVar));
    }

    private RelationshipsInfo fillPkb(StatementList stmtList) throws UnknownStatementType {
        Statement firstStmt = (Statement) pkb.getAst().setChild(stmtList, stmtList.getStatements().get(0));
        RelationshipsInfo stmtListRelationshipsInfo = fillPkb(firstStmt);

        Statement currentStmt = firstStmt;

        for (int i = 1; i < stmtList.getStatements().size(); i++) {
            pkb.getFollowsInterface().setFollows(currentStmt, stmtList.getStatements().get(i));
            currentStmt = (Statement) pkb.getAst().setSibling(currentStmt, stmtList.getStatements().get(i));
            stmtListRelationshipsInfo = RelationshipsInfo.merge(stmtListRelationshipsInfo, fillPkb(currentStmt));
        }

        return stmtListRelationshipsInfo;
    }

    private RelationshipsInfo fillPkb(Statement stmt) throws UnknownStatementType {
        if (stmt instanceof Assignment) {
            return fillPkb((Assignment) stmt);
        } else if (stmt instanceof While) {
            return fillPkb((While) stmt);
        } else if (stmt instanceof If) {
            return RelationshipsInfo.emptyInfo();
            //todo w przyszłych iteracjach
        } else if (stmt instanceof Call) {
            return RelationshipsInfo.emptyInfo();
            //todo w przyszłych iteracjach
        } else {
            throw new UnknownStatementType();
        }
    }

    private RelationshipsInfo fillPkb(While whileStmt) throws UnknownStatementType {
        Variable conditionVar = (Variable) pkb.getAst().setChild(whileStmt, whileStmt.getConditionVar());
        RelationshipsInfo relationshipsInfo = fillPkb(conditionVar, whileStmt.getStmtList());

        relationshipsInfo.getModifiedVariables()
                .forEach(modifiedVar -> pkb.getModifiesInterface().setModifies(whileStmt, modifiedVar));

        pkb.getUsesInterface().setUses(whileStmt, conditionVar);
        relationshipsInfo.getUsedVariables()
                .forEach(usedVar -> pkb.getUsesInterface().setUses(whileStmt, usedVar));

        whileStmt.getStmtList().getStatements()
                .forEach(statement -> pkb.getParentInterface().setParent(whileStmt, statement));

        return relationshipsInfo;
    }

    private RelationshipsInfo fillPkb(Assignment assignment) {
        VariableImpl variable = (VariableImpl) pkb.getAst().setChild(assignment, assignment.getVariable());

        RelationshipsInfo relationshipsInfo = new RelationshipsInfo();

        relationshipsInfo.addModifiedVar(variable);
        pkb.getModifiesInterface().setModifies(assignment, variable);

        RelationshipsInfo.merge(relationshipsInfo, fillPkb(variable, assignment.getExpression()));
        relationshipsInfo.getUsedVariables()
                .forEach(usedVar -> pkb.getUsesInterface().setUses(assignment, usedVar));
        return relationshipsInfo;
    }

    private RelationshipsInfo fillPkb(Variable variable, Expression expression) {
        pkb.getVarTable().insertVar(variable.getName());
        expression = (Expression) pkb.getAst().setSibling(variable, expression);
        return fillPkb(expression);
    }

    private RelationshipsInfo fillPkb(Variable variable, StatementList stmtList) throws UnknownStatementType {
        pkb.getVarTable().insertVar(variable.getName());
        stmtList = (StatementList) pkb.getAst().setSibling(variable, stmtList);
        return fillPkb(stmtList);
    }

    private RelationshipsInfo fillPkb(Expression expression) {
        if (expression.getExpression() != null) {
            RelationshipsInfo relationshipsInfo = new RelationshipsInfo();
            if (expression.getFactor() instanceof Variable) {
                Variable variable = (Variable) expression.getFactor();
                pkb.getVarTable().insertVar(variable.getName());
                relationshipsInfo.addUsedVar(variable);
            }
            Factor factor = (Factor) pkb.getAst().setChild(expression, expression.getFactor());

            Expression nestedExpression = (Expression) pkb.getAst().setSibling(factor, expression.getExpression());
            return RelationshipsInfo.merge(relationshipsInfo, fillPkb(nestedExpression));
        } else return RelationshipsInfo.emptyInfo();
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

    private void parseChar(char c) throws MissingCharacterException {//todo do codeScannera (zadanie #11)
        parseChar(c, true);
    }

    private void parseChar(char c, boolean incFlag) throws MissingCharacterException {//todo do codeScannera (zadanie #11)
        this.codeScanner.skipWhitespaces();
        if (this.codeScanner.hasCurrentChar(c)) {
            if (incFlag) {
                this.codeScanner.incrementPosition();
            }
        } else {
            throw new MissingCharacterException(c, this.codeScanner.getCurrentPosition());
        }
    }

    private String parseName() throws SimpleParserException {//todo do codeScannera (zadanie #11)
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

    private char parseDigit() throws SimpleParserException {//todo do codeScannera (zadanie #11)
        char digit = this.codeScanner.getCurrentDigit();
        this.codeScanner.incrementPosition();
        return digit;
    }

    private char parseLetter() throws SimpleParserException {//todo do codeScannera (zadanie #11)
        char letter = this.codeScanner.getCurrentLetter();
        this.codeScanner.incrementPosition();
        return letter;
    }

    private void parseKeyword(EntityType keyword) throws MissingCodeEntityException {//todo do codescannera, ale jako argument string (zadanie #11)
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

        for(Integer i = 1; i < stmtList.size()+1; i++){

            stmtList.get(i-1).setStmtLine(i-1);

        }
        return new StatementListImpl(stmtList);
    }

    private Statement parseStmt() throws SimpleParserException {
        codeScanner.skipWhitespaces();
        String firstWord = parseName();
        System.out.println(firstWord);

        codeScanner.skipWhitespaces();
        if (this.codeScanner.hasCurrentChar(EntityType.EQUALS.getETName().charAt(0))) {
            this.codeScanner.incrementPosition();
            this.counter ++;
            System.out.println(this.counter);
            return parseAssignmentAfterEquals(firstWord);
        } else if (EntityType.IF.getETName().equals(firstWord)) {
            return parseIf();
        } else if (EntityType.WHILE.getETName().equals(firstWord)) {
            this.counter++;
            System.out.println(this.counter);
            return parseWhile();
        } else throw new UnknownStatementType(codeScanner.getCurrentPosition());
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
            return new ExpressionImpl(factor);
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
