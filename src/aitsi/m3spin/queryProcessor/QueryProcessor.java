package aitsi.m3spin.queryProcessor;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.AssignmentImpl;
import aitsi.m3spin.commons.impl.StatementImpl;
import aitsi.m3spin.commons.impl.VariableImpl;
import aitsi.m3spin.commons.impl.WhileImpl;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.parser.CodeScanner;
import aitsi.m3spin.parser.exception.MissingCharacterException;
import aitsi.m3spin.parser.exception.SimpleParserException;

import java.util.ArrayList;
import java.util.List;

public class QueryProcessor {
    private CodeScanner codeScanner;
    private List<TNode> declarationList = new ArrayList<TNode>();

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
        ////
       String firstWord = parseName();
       codeScanner.skipWhitespaces();

       if (EntityType.ASSIGNMENT.getETName().equals(firstWord)) {
           //tworzenie listy obiektów 1. zliczyc ilosć, do średnika ;
           parseAssignment();

       }
//       else if(EntityType.WHILE.getETName().equals(firstWord)){
////           parseWhile();
//       }
       else if(EntityType.STATEMENT.getETName().equals(firstWord)){
//           parseStatement();
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
    private void parseAssignment() throws SimpleParserException{
        while (!this.codeScanner.hasCurrentChar(';'))
        {
                codeScanner.skipWhitespaces();
                String firstWord = parseName();
                //utwórz instancje obiektu Assign i dodaj na liste obiektów
                declarationList.add(new AssignmentImpl(new VariableImpl(firstWord)));
                //pominięcie ','
                parseChar(',',true);
                codeScanner.skipWhitespaces();
        }
    }

    private void parseStatement() throws SimpleParserException{
        while (!this.codeScanner.hasCurrentChar(';'))
        {
            codeScanner.skipWhitespaces();
            String firstWord = parseName();
//            declarationList.add(new StatementImpl((firstWord));
            parseChar(',',true);
            codeScanner.skipWhitespaces();
        }
    }

    private void parseWhile() throws SimpleParserException{
        while (!this.codeScanner.hasCurrentChar(';'))
        {
            codeScanner.skipWhitespaces();
            String firstWord = parseName();
            //utwórz instancje obiektu Assign i dodaj na liste obiektów
            declarationList.add(new WhileImpl(new VariableImpl(firstWord),null));
            //pominięcie ','
            parseChar(',',true);
            codeScanner.skipWhitespaces();
        }
    }



}
