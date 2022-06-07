package aitsi.m3spin.query.preprocessor;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.exception.CodeScannerException;
import aitsi.m3spin.commons.exception.IllegalCharacterException;
import aitsi.m3spin.commons.exception.MissingCharacterException;
import aitsi.m3spin.query.QueryProcessorException;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisonException;
import aitsi.m3spin.query.model.PqlEntityEnum;
import aitsi.m3spin.query.model.Query;
import aitsi.m3spin.query.model.clauses.Pattern;
import aitsi.m3spin.query.model.clauses.SuchThat;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.enums.AttributeEnum;
import aitsi.m3spin.query.model.enums.AttributeTypeEnum;
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

    public void parsePql() throws CodeScannerException, QueryProcessorException {
        parseDeclarations();
        parseQueries();
    }

    private void parseDeclarations() throws CodeScannerException, UnknownSynonymType {
        while (!codeScanner.hasCurrentString(PqlEntityEnum.SELECT.getName())) {
            codeScanner.skipWhitespaces();
            String synonymType = parseSynonymType();
            codeScanner.skipWhitespaces();

            if (EntityType.contains(synonymType)) parseDeclaration(EntityType.fromString(synonymType));
            else throw new UnknownSynonymType(synonymType);
        }
    }

    private String parseSynonymType() throws CodeScannerException {
        return codeScanner.parseName('_');
    }

    private void parseQueries() throws QueryProcessorException, CodeScannerException {
        String selectName = PqlEntityEnum.SELECT.getName();
        while (codeScanner.hasCurrentString(selectName)) {
            this.queryList.add(parseQuery());
        }
    }

    private void parseDeclaration(EntityType entityType) throws CodeScannerException {
        char synonymDelimiter = PqlEntityEnum.SYNONYM_DELIMITER.getName().charAt(0);
        char declarationDelimiter = PqlEntityEnum.DECLARATION_DELIMITER.getName().charAt(0);
        String expectedDelimiters = String.format("synonym delimiter '%s' or declaration delimiter '%s'",
                synonymDelimiter, declarationDelimiter);

        while (!this.codeScanner.hasCurrentString(PqlEntityEnum.DECLARATION_DELIMITER.getName())) {
            codeScanner.skipWhitespaces();
            String firstWord = codeScanner.parseName();
            declarationList.add(new Synonym(firstWord, entityType));
            codeScanner.skipWhitespaces();

            if (!codeScanner.hasCurrentChar())
                throw new MissingCharacterException(expectedDelimiters, codeScanner.getCurrentPosition());
            char actualDelimiter = codeScanner.getCurrentChar();
            if (actualDelimiter == synonymDelimiter) codeScanner.parseChar(synonymDelimiter);
            else if (actualDelimiter == declarationDelimiter) break;
            else
                throw new IllegalCharacterException(actualDelimiter, codeScanner.getCurrentPosition(), expectedDelimiters);
        }
        codeScanner.parseChar(PqlEntityEnum.DECLARATION_DELIMITER.getName().charAt(0));
    }

    private Query parseQuery() throws QueryProcessorException, CodeScannerException {
        codeScanner.skipWhitespaces();
        return new Query(parseSelectedSynonym(), parseSuchThatList(), parseWithList(), parsePatternList());
    }

    private Synonym parseSelectedSynonym() throws SynonymNotDeclared, CodeScannerException {
        codeScanner.parseKeyword(PqlEntityEnum.SELECT.getName());
        String parsedName = codeScanner.parseName();
        return findDeclaredSynonym(parsedName);
    }

    private List<SuchThat> parseSuchThatList() throws QueryPreprocessorException, CodeScannerException {
        codeScanner.skipWhitespaces();
        String suchThatName = PqlEntityEnum.SUCH_THAT.getName();
        if (codeScanner.hasCurrentString(suchThatName)) {
            codeScanner.parseKeyword(suchThatName);
            codeScanner.skipWhitespaces();

            String parsedRelationName = parseRelationName();

            RelationshipPreprocEnum relationEnum = validateRelationshipName(parsedRelationName);

            codeScanner.skipWhitespaces();
            codeScanner.parseChar('(');
            RelationshipArgumentRef firstRelationArgument = parseRelationArgument(relationEnum, true);
            codeScanner.parseChar(',');
            codeScanner.skipWhitespaces();
            RelationshipArgumentRef secondRelationArgument = parseRelationArgument(relationEnum, false);
            codeScanner.parseChar(')');

            return Collections.singletonList(new SuchThat(relationEnum, firstRelationArgument, secondRelationArgument));
        } else {
            return Collections.emptyList();
        }
    }

    private RelationshipPreprocEnum validateRelationshipName(String parsedRelationName) throws UnknownRelationTypeException {
        Optional<RelationshipPreprocEnum> relationEnumOptional = Stream.of(RelationshipPreprocEnum.values())
                .filter(re -> re.getRelationName().equals(parsedRelationName))
                .findFirst();
        RelationshipPreprocEnum relationEnum;

        if (relationEnumOptional.isPresent()) {
            relationEnum = relationEnumOptional.get();
        } else {
            throw new UnknownRelationTypeException(parsedRelationName);
        }
        return relationEnum;
    }

    private List<WithClause> parseWithList() throws QueryPreprocessorException, CodeScannerException, IncompatibleTypesComparisonException {
        codeScanner.skipWhitespaces();
        String withName = PqlEntityEnum.WITH.getName();
        if (codeScanner.hasCurrentString(withName)) {
            codeScanner.parseKeyword(withName);

            WithArgumentRef leftHandRef = parseWithArgument();
            codeScanner.skipWhitespaces();
            codeScanner.parseChar('=');
            WithArgumentRef rightHandRef = parseWithArgument();

            AttributeTypeEnum leftHandType = leftHandRef.getWithValueType();
            AttributeTypeEnum rightHandType = rightHandRef.getWithValueType();
            if (!leftHandType.equals(rightHandType))
                throw new IncompatibleTypesComparisonException(leftHandType, rightHandType);

            return Collections.singletonList(new WithClause(leftHandRef, rightHandRef));
        } else {
            return Collections.emptyList();
        }
    }

    private List<Pattern> parsePatternList() {
        return Collections.emptyList();//todo w przyszych iteracjach
    }

    private Synonym findDeclaredSynonym(String synonymName) throws SynonymNotDeclared {
        Optional<Synonym> declarationOptional = declarationList.stream()
                .filter(d -> d.getName().equals(synonymName))
                .findFirst();
        if (declarationOptional.isPresent()) {
            return declarationOptional.get();
        } else {
            throw new SynonymNotDeclared(synonymName);
        }
    }

    private RelationshipArgumentRef parseRelationArgument(RelationshipPreprocEnum relationEnum, boolean isFirstArgument)
            throws QueryPreprocessorException, CodeScannerException {
        Set<ReferenceType> allowedRefTypes;
        if (isFirstArgument) allowedRefTypes = relationEnum.getAllowedFirstArgTypes();
        else allowedRefTypes = relationEnum.getAllowedSecondArgTypes();

        if (codeScanner.hasCurrentChar('"')) {
            if (allowedRefTypes.contains(ReferenceType.STRING)) {
                return parseIdent();
            }
            throw BadRelationshipArgumentTypeException.ofNotAllowedRefType(relationEnum, ReferenceType.STRING, isFirstArgument);
        } else if (Character.isLetter(codeScanner.getCurrentChar())) {
            if (allowedRefTypes.contains(ReferenceType.SYNONYM)) {
                return parseSynonym();
            }
            throw BadRelationshipArgumentTypeException.ofNotAllowedRefType(relationEnum, ReferenceType.SYNONYM, isFirstArgument);
        } else if (Character.isDigit(codeScanner.getCurrentChar())) {
            if (allowedRefTypes.contains(ReferenceType.SYNONYM)) {
                return new IntegerReference(parseConst());
            }
            throw BadRelationshipArgumentTypeException.ofNotAllowedRefType(relationEnum, ReferenceType.INTEGER, isFirstArgument);
        } else if (codeScanner.hasCurrentChar('_')) {
            if (allowedRefTypes.contains(ReferenceType.SYNONYM)) {
                return new WildcardReference();
            }
            throw BadRelationshipArgumentTypeException.ofNotAllowedRefType(relationEnum, ReferenceType.WILDCARD, isFirstArgument);
        }
        throw BadRelationshipArgumentTypeException.ofUnknownRefType(relationEnum, isFirstArgument);
    }

    private WithArgumentRef parseWithArgument() throws QueryPreprocessorException, CodeScannerException {
        codeScanner.skipWhitespaces();
        if (codeScanner.hasCurrentChar('"')) {
            return parseIdent();
        } else if (Character.isLetter(codeScanner.getCurrentChar())) {
            Synonym synonym = parseSynonym();
            if (codeScanner.getCurrentChar() == '.') {
                codeScanner.parseChar('.');
                AttributeEnum attributeEnum = parseAttributeName(synonym);
                return new AttributeReference(synonym, attributeEnum);
            } else return synonym;
        } else if (Character.isDigit(codeScanner.getCurrentChar())) {
            return new IntegerReference(parseConst());
        } else throw new UnknownWithReferenceException(codeScanner.getCurrentChar());
    }

    private AttributeEnum parseAttributeName(Synonym synonym) throws QueryPreprocessorException, CodeScannerException {
        String parsedName = codeScanner.parseName('#');

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

    private Synonym parseSynonym() throws SynonymNotDeclared, CodeScannerException {
        String synonymName = codeScanner.parseName();
        return findDeclaredSynonym(synonymName);
    }

    private StringReference parseIdent() throws CodeScannerException {
        codeScanner.parseChar('"');
        StringReference stringReference = new StringReference(codeScanner.parseName());
        codeScanner.parseChar('"');
        return stringReference;
    }

    private int parseConst() throws CodeScannerException {//todo ATS-11
        StringBuilder name = new StringBuilder(String.valueOf(codeScanner.parseDigit()));
        while (codeScanner.hasCurrentChar()) {
            char currentChar = codeScanner.getCurrentChar();
            if (Character.isDigit(currentChar)) {
                name.append(codeScanner.parseDigit());
            } else {
                break;
            }
        }
        return Integer.parseInt(String.valueOf(name));
    }

    private String parseRelationName() throws CodeScannerException {
        return codeScanner.parseName('*');
    }
}
