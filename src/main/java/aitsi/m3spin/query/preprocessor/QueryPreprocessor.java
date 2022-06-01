package aitsi.m3spin.query.preprocessor;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.exception.CodeScannerException;
import aitsi.m3spin.commons.exception.MissingCharacterException;
import aitsi.m3spin.query.model.PqlEntityEnum;
import aitsi.m3spin.query.model.Query;
import aitsi.m3spin.query.model.clauses.Pattern;
import aitsi.m3spin.query.model.clauses.SuchThat;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.enums.AttributeEnum;
import aitsi.m3spin.query.model.enums.RelationshipPreprocEnum;
import aitsi.m3spin.query.model.references.*;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import aitsi.m3spin.query.preprocessor.exceptions.*;
import aitsi.m3spin.spafrontend.parser.CodeScanner;
import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

public class QueryPreprocessor {
    private final CodeScanner codeScanner;
    private final List<Synonym> declarationList = new ArrayList<>();
    @Getter
    private final List<Query> queryList = new ArrayList<>();

    public QueryPreprocessor(List<String> code) {
        this.codeScanner = new CodeScanner(code);
    }

    //TODO zmienić exception
    public void parsePql() throws CodeScannerException, QueryPreprocessorException {
        parseSynonyms();
        parseQueries();
    }

    //todo ogarnac wyjatki
    private void parseQueries() throws QueryPreprocessorException, CodeScannerException {
        while (PqlEntityEnum.SELECT.getPqlEntityName().equals(codeScanner.getCurrentString(6))) {
            this.queryList.add(parseQuery());
        }
    }

    private Query parseQuery() throws QueryPreprocessorException, CodeScannerException {
        codeScanner.skipWhitespaces();
        return new Query(parseSelectedSynonym(), parseSuchThatList(), parseWithList(), parsePatternList());
    }

    private List<Pattern> parsePatternList() {
        return null;//todo w przyszych iteracjach
    }

    private List<WithClause> parseWithList() throws QueryPreprocessorException, CodeScannerException {
        codeScanner.skipWhitespaces();
        if (PqlEntityEnum.WITH.getPqlEntityName().equals(codeScanner.getCurrentString(4, false))) {
            codeScanner.incrementPosition(4);
            WithArgumentRef leftHandRef = parseWithArgument();
            codeScanner.skipWhitespaces();
            parseChar('=');
            WithArgumentRef rightHandRef = parseWithArgument();
            return Collections.singletonList(new WithClause(leftHandRef, rightHandRef));
        } else {
            return Collections.emptyList();
        }
    }

    private List<SuchThat> parseSuchThatList() throws QueryPreprocessorException, CodeScannerException {
        codeScanner.skipWhitespaces();
        if (PqlEntityEnum.SUCH_THAT.getPqlEntityName()
                .equals(codeScanner.getCurrentString(9)
                )) {
            codeScanner.skipWhitespaces();
            String parsedRelationName = parseRelationName();

            Optional<RelationshipPreprocEnum> relationEnumOptional = Stream.of(RelationshipPreprocEnum.values())
                    .filter(re -> re.getRelationName().equals(parsedRelationName))
                    .findFirst();
            RelationshipPreprocEnum relationEnum;

            if (relationEnumOptional.isPresent()) {
                relationEnum = relationEnumOptional.get();
            } else {
                throw new UnknownRelationTypeException(parsedRelationName);
            }
            codeScanner.skipWhitespaces();
            parseChar('(');
            RelationshipArgumentRef firstRelationArgument = parseRelationArgument(relationEnum, true);
            parseChar(',');
            codeScanner.skipWhitespaces();
            RelationshipArgumentRef secondRelationArgument = parseRelationArgument(relationEnum, false);
            parseChar(')');

            return Collections.singletonList(new SuchThat(relationEnum, firstRelationArgument, secondRelationArgument));
        } else {
            return Collections.emptyList();
        }
    }


    private RelationshipArgumentRef parseRelationArgument(RelationshipPreprocEnum relationEnum, boolean isFirstArgument) throws QueryPreprocessorException, CodeScannerException {
        Set<ReferenceType> referenceTypes = isFirstArgument ? relationEnum.getAllowedFirstArgTypes() : relationEnum.getAllowedSecondArgTypes();
        if (codeScanner.hasCurrentChar('"')) {
            if (referenceTypes.contains(ReferenceType.STRING)) {
                return parseIdent();
            } else throw new BadRelationshipArgumentTypeException(relationEnum, "String");
        } else if (Character.isLetter(codeScanner.getCurrentChar())) {
            if (referenceTypes.contains(ReferenceType.SYNONYM)) {
                return parseSynonym();
            } else throw new BadRelationshipArgumentTypeException(relationEnum, "Synonim");
        } else if (Character.isDigit(codeScanner.getCurrentChar())) {
            if (referenceTypes.contains(ReferenceType.SYNONYM)) {
                return new IntegerReference(parseConst());
            } else throw new BadRelationshipArgumentTypeException(relationEnum, "Integer");
        } else if (codeScanner.hasCurrentChar('_')) {
            if (referenceTypes.contains(ReferenceType.SYNONYM)) {
                return new WildcardReference();
            } else throw new BadRelationshipArgumentTypeException(relationEnum, "Wildcard(_)");
        }
        return null;
    }

    private WithArgumentRef parseWithArgument() throws QueryPreprocessorException, CodeScannerException {
        codeScanner.skipWhitespaces();
        if (codeScanner.hasCurrentChar('"')) {
            return parseIdent();
        } else if (Character.isLetter(codeScanner.getCurrentChar())) {
            Synonym synonym = parseSynonym();
            if (codeScanner.getCurrentChar() == '.') {
                parseChar('.');
                AttributeEnum attributeEnum = parseAttributeName(synonym);
                return new AttributeReference(synonym, attributeEnum);
            } else return synonym;
        } else if (Character.isDigit(codeScanner.getCurrentChar())) {
            return new IntegerReference(parseConst());
        } else throw new UnknownWithReferenceException(codeScanner.getCurrentChar());
    }

    private AttributeEnum parseAttributeName(Synonym synonym) throws QueryPreprocessorException, CodeScannerException {
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
        Optional<AttributeEnum> attributeEnumOptional = Stream.of(AttributeEnum.values())
                .filter(re -> re.getAttrName().equals(parsedName))
                .findFirst();
        if (attributeEnumOptional.isPresent()) {
            AttributeEnum attributeEnum = attributeEnumOptional.get();
            if (!attributeEnum.getEntityType().equals(synonym.getSynonymType()))
                throw new BadAttributeException(attributeEnum, synonym);
            return attributeEnum;
        } else {
            throw new UnknownRelationTypeException(parsedName);
        }
    }

    private Synonym parseSynonym() throws NameNotDeclaredException, CodeScannerException {
        String synonymName = parseName();
        return findDeclaredSynonym(synonymName);
    }

    private StringReference parseIdent() throws CodeScannerException {
        parseChar('"');
        StringReference stringReference = new StringReference(parseName());
        parseChar('"');
        return stringReference;
    }

    private int parseConst() throws CodeScannerException {
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

    private String parseRelationName() throws CodeScannerException {
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

    private Synonym parseSelectedSynonym() throws NameNotDeclaredException, CodeScannerException {
        String parsedName = parseName();
        return findDeclaredSynonym(parsedName);
    }

    private Synonym findDeclaredSynonym(String synonymName) throws NameNotDeclaredException {
        Optional<Synonym> declarationOptional = declarationList.stream()
                .filter(d -> d.getName().equals(synonymName))
                .findFirst();
        if (declarationOptional.isPresent()) {
            return declarationOptional.get();
        } else {
            throw new NameNotDeclaredException(synonymName);
        }
    }

    private String parseName() throws CodeScannerException {//todo wyciągnąc zduplikowany kod do CodeScannera
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

    private char parseDigit() throws CodeScannerException {//todo do CodeScannera stąd i z parsera
        char digit = this.codeScanner.getCurrentDigit();
        return parseChar(digit);
    }

    private char parseLetter() throws CodeScannerException {//todo do CodeScannera stąd i z parsera
        char letter = this.codeScanner.getCurrentLetter();
        return parseChar(letter);
    }


    private void parseSynonyms() throws CodeScannerException, UnknownSynonymType {
        while (!this.codeScanner.hasCurrentChar('S')) {
            codeScanner.skipWhitespaces();
            String firstWord = parseName();
            codeScanner.skipWhitespaces();

            if (EntityType.contains(firstWord)) parseSynonym(EntityType.valueOf(firstWord));
            else throw new UnknownSynonymType(firstWord);
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


    private void parseSynonym(EntityType entityType) throws CodeScannerException {
        while (!this.codeScanner.hasCurrentChar(';')) {
            codeScanner.skipWhitespaces();
            String firstWord = parseName();
            //utwórz instancje obiektu Assign i dodaj na liste obiektów
            declarationList.add(new Synonym(firstWord, entityType));
            //pominięcie ','
            if (this.codeScanner.hasCurrentChar(','))
                parseChar(',', true);
            codeScanner.skipWhitespaces();
        }
        codeScanner.incrementPosition();
    }


}
