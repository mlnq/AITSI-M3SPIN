package aitsi.m3spin.queryProcessor;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.parser.CodeScanner;
import aitsi.m3spin.parser.exception.MissingCharacterException;
import aitsi.m3spin.parser.exception.SimpleParserException;
import aitsi.m3spin.queryProcessor.model.Declaration;
import aitsi.m3spin.queryProcessor.model.Query;

import java.util.ArrayList;
import java.util.List;

public class QueryProcessor {
    private CodeScanner codeScanner;
    private List<Declaration> declarationList = new ArrayList<Declaration>();
    private List<Query> queryList = new ArrayList<Query>();

    public QueryProcessor(List<String> code) {
        this.codeScanner = new CodeScanner(code);
    }

    //TODO zmienić exception
    public void parse() throws SimpleParserException{
        parseDeclaration();
    }

    private String parseName() throws SimpleParserException {
        StringBuilder name = new StringBuilder(String.valueOf(parseLetter()));

        while (codeScanner.hasCurrentChar()) {
            char currentChar = codeScanner.getCurrentChar();
            if (Character.isLetter(currentChar)) {
                name.append(parseLetter());
            }
            else if (Character.isDigit(currentChar)) {
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


   private void parseDeclaration() throws SimpleParserException {
       String firstWord = parseName();
       codeScanner.skipWhitespaces();

//       Stream.of(EntityType.values())
//               .anyMatch(value => {})

       if (EntityType.ASSIGNMENT.getETName().equals(firstWord)) {
           //tworzenie listy obiektów 1. zliczyc ilosć, do średnika ;
           parseDeclaration(EntityType.ASSIGNMENT);
       }
       else if(EntityType.WHILE.getETName().equals(firstWord)){
           parseDeclaration(EntityType.WHILE);
       }
       else if(EntityType.STATEMENT.getETName().equals(firstWord)){
           parseDeclaration(EntityType.STATEMENT);
       }else if(EntityType.VARIABLE.getETName().equals(firstWord)){
           parseDeclaration(EntityType.VARIABLE);
       }else if(EntityType.CONSTANT.getETName().equals(firstWord)){
           parseDeclaration(EntityType.CONSTANT);
       }else if(EntityType.PROG_LINE.getETName().equals(firstWord)){
           parseDeclaration(EntityType.PROG_LINE);
       }
   }

    private void parseChar(char c,boolean incFlag) throws MissingCharacterException {
        this.codeScanner.skipWhitespaces();

        if (this.codeScanner.hasCurrentChar(c))
        {
            if(incFlag) this.codeScanner.incrementPosition();
        }
        else throw new MissingCharacterException(c, this.codeScanner.getCurrentPosition());
    }



   //zmienić exceptiony
    private void parseDeclaration(EntityType entityType) throws SimpleParserException{
        while (!this.codeScanner.hasCurrentChar(';'))
        {
            codeScanner.skipWhitespaces();
            String firstWord = parseName();
            //utwórz instancje obiektu Assign i dodaj na liste obiektów
            declarationList.add(new Declaration(firstWord,entityType));
            //pominięcie ','
            if(this.codeScanner.hasCurrentChar(','))
                parseChar(',',true);
            codeScanner.skipWhitespaces();
        }
    }





}
