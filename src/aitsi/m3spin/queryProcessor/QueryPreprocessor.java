package aitsi.m3spin.queryProcessor;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.parser.CodeScanner;
import aitsi.m3spin.parser.exception.MissingCharacterException;
import aitsi.m3spin.parser.exception.SimpleParserException;
import aitsi.m3spin.queryProcessor.exceptions.NameNotDeclaredException;
import aitsi.m3spin.queryProcessor.exceptions.UnknownRelationTypeException;
import aitsi.m3spin.queryProcessor.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class QueryPreprocessor {

    private CodeScanner codeScanner;
    private List<Declaration> declarationList = new ArrayList<Declaration>();
    private List<Query> queryList = new ArrayList<Query>();

    public QueryPreprocessor(List<String> code) {
        this.codeScanner = new CodeScanner(code);
    }

    //TODO zmienić exception
    public void parse() throws SimpleParserException, NameNotDeclaredException, UnknownRelationTypeException {
        parseDeclarations();
        parseQueries();
    }

    //todo ogarnac wyjatki
    private void parseQueries() throws SimpleParserException, NameNotDeclaredException, UnknownRelationTypeException {
        while (PqlEntityEnum.SELECT.getPqlEntityName().equals(codeScanner.getCurrentString(6))) {
            this.queryList.add(parseQuery());
        }
    }

    private Query parseQuery() throws SimpleParserException, NameNotDeclaredException, UnknownRelationTypeException {
        codeScanner.skipWhitespaces();
        Query query = new Query(parseSelectedDeclaration(), parseSuchThatList(), parseWithList());
        return query;
    }

    private List<WithClause> parseWithList() throws SimpleParserException, NameNotDeclaredException, UnknownRelationTypeException {
        codeScanner.skipWhitespaces();
        if (PqlEntityEnum.WITH.getPqlEntityName().equals(codeScanner.getCurrentString(4, false))) {
            codeScanner.incrementPosition(4);
            codeScanner.skipWhitespaces();
            Declaration withDeclaration = parseSelectedDeclaration();
            parseChar('.', true);
            AttributeNameEnum parsedAttributeName = parseAttributeName();
            codeScanner.skipWhitespaces();
            parseChar('=');
            codeScanner.skipWhitespaces();
            RelationArgument withArgument = parseRelationArgument();

            return Collections.singletonList(new WithClause(withDeclaration, parsedAttributeName, withArgument));
        } else {
            return Collections.emptyList();
        }
    }

    private List<SuchThat> parseSuchThatList() throws SimpleParserException, UnknownRelationTypeException, NameNotDeclaredException {
        codeScanner.skipWhitespaces();
        if (PqlEntityEnum.SUCH_THAT.getPqlEntityName()
                .equals(codeScanner.getCurrentString(9)
                )) {
            codeScanner.skipWhitespaces();
            String parsedRelationName = parseRelationName();

            Optional<RelationEnum> relationEnumOptional = Stream.of(RelationEnum.values())
                    .filter(re -> re.getRelationName().equals(parsedRelationName))
                    .findFirst();
            RelationEnum relationEnum;

            if (relationEnumOptional.isPresent()) {
                relationEnum = relationEnumOptional.get();
            } else {
                throw new UnknownRelationTypeException(parsedRelationName);
            }
            codeScanner.skipWhitespaces();
            parseChar('(');
            RelationArgument firstRelationArgument = parseRelationArgument();
            parseChar(',');
            codeScanner.skipWhitespaces();
            RelationArgument secondRelationArgument = parseRelationArgument();
            parseChar(')');
            codeScanner.getCurrentPosition();

            return Collections.singletonList(new SuchThat(relationEnum, firstRelationArgument, secondRelationArgument));
        } else {
            return Collections.emptyList();
        }
    }

    private AttributeNameEnum parseAttributeName() throws SimpleParserException, UnknownRelationTypeException {
        StringBuilder name = new StringBuilder(String.valueOf(parseLetter()));

        while (codeScanner.hasCurrentChar()) {
            char currentChar = codeScanner.getCurrentChar();
            if (Character.isLetter(currentChar)) {
                name.append(parseLetter());
            } else if (Character.isDigit(currentChar)) {
                name.append(parseDigit());
            } else if (currentChar == '#') {
                name.append(parseChar('#'));
            } else {
                break;
            }
        }
        String parsedName = String.valueOf(name);
        Optional<AttributeNameEnum> attributeNameEnumOptional = Stream.of(AttributeNameEnum.values())
                .filter(re -> re.getAttrName().equals(parsedName))
                .findFirst();
        if (attributeNameEnumOptional.isPresent()) {
            return attributeNameEnumOptional.get();
        } else {
            throw new UnknownRelationTypeException(parsedName);
        }
    }

    //TODO ogarnąć jakie '"' są w pql
    private RelationArgument parseRelationArgument() throws SimpleParserException {
        if(codeScanner.hasCurrentChar('"')){
            codeScanner.incrementPosition();
            String simpleEntityName = parseName();
            parseChar(codeScanner.getCurrentChar());
            codeScanner.getCurrentPosition();
            return new SimpleEntityName(simpleEntityName);
        } else if (Character.isLetter(codeScanner.getCurrentChar())) {
            return new Declaration(parseName());
        } else if (Character.isDigit(codeScanner.getCurrentChar())) {
            return new Constant(parseConst());
        } else return null;
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

    private String parseRelationName() throws SimpleParserException {
        StringBuilder name = new StringBuilder(String.valueOf(parseLetter()));

        while (codeScanner.hasCurrentChar()) {
            char currentChar = codeScanner.getCurrentChar();
            if (Character.isLetter(currentChar)) {
                name.append(parseLetter());
            } else if (Character.isDigit(currentChar)) {
                name.append(parseDigit());
            } else if (currentChar == '*') {
                name.append(parseChar('*'));

            } else {
                break;
            }
        }
        return String.valueOf(name);
    }

    private Declaration parseSelectedDeclaration() throws SimpleParserException, NameNotDeclaredException {
        String parsedName = parseName();
        Optional<Declaration> declarationOptional = declarationList.stream()
                .filter(d -> d.getName().equals(parsedName))
                .findFirst();
        if (declarationOptional.isPresent()) {
            return declarationOptional.get();
        } else {
            throw new NameNotDeclaredException(parsedName);
        }
    }

    private String parseName() throws SimpleParserException {//todo wyciągnąc zduplikowany kod
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

    private char parseDigit() throws SimpleParserException {//todo do CodeScannera stąd i z parsera
        char digit = this.codeScanner.getCurrentDigit();
        return parseChar(digit);
    }

    private char parseLetter() throws SimpleParserException {//todo do CodeScannera stąd i z parsera
        char letter = this.codeScanner.getCurrentLetter();
        return parseChar(letter);

    }


    private void parseDeclarations() throws SimpleParserException {
        while (!this.codeScanner.hasCurrentChar('S')) {
            codeScanner.skipWhitespaces();
            String firstWord = parseName();
            codeScanner.skipWhitespaces();

            //       Stream.of(EntityType.values())
            //               .anyMatch(value => {})
            if (EntityType.ASSIGNMENT.getETName().equals(firstWord)) {
                //tworzenie listy obiektów 1. zliczyc ilosć, do średnika ;
                parseDeclaration(EntityType.ASSIGNMENT);
            } else if (EntityType.WHILE.getETName().equals(firstWord)) {
                parseDeclaration(EntityType.WHILE);
            } else if (EntityType.STATEMENT.getETName().equals(firstWord)) {
                parseDeclaration(EntityType.STATEMENT);
            } else if (EntityType.VARIABLE.getETName().equals(firstWord)) {
                parseDeclaration(EntityType.VARIABLE);
            } else if (EntityType.CONSTANT.getETName().equals(firstWord)) {
                parseDeclaration(EntityType.CONSTANT);
            } else if (EntityType.PROG_LINE.getETName().equals(firstWord)) {
                parseDeclaration(EntityType.PROG_LINE);
            }
        }
    }

    private char parseChar(char c, boolean incFlag) throws MissingCharacterException { //todo do CodeScannera stąd i z parsera
        this.codeScanner.skipWhitespaces();

        if (this.codeScanner.hasCurrentChar(c)) {
            if (incFlag) this.codeScanner.incrementPosition();
            return c;
        } else throw new MissingCharacterException(c, this.codeScanner.getCurrentPosition());
    }

    private char parseChar(char c) throws MissingCharacterException {//todo do CodeScannera stąd i z parsera
        return parseChar(c, true);
    }


    //zmienić exceptiony
    private void parseDeclaration(EntityType entityType) throws SimpleParserException {
        while (!this.codeScanner.hasCurrentChar(';')) {
            codeScanner.skipWhitespaces();
            String firstWord = parseName();
            //utwórz instancje obiektu Assign i dodaj na liste obiektów
            declarationList.add(new Declaration(firstWord, entityType));
            //pominięcie ','
            if (this.codeScanner.hasCurrentChar(','))
                parseChar(',', true);
            codeScanner.skipWhitespaces();
        }
        codeScanner.incrementPosition();
    }


}
