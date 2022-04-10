package aitsi.m3spin.parser;

import aitsi.m3spin.parser.exception.IllegalCharacterException;
import aitsi.m3spin.parser.exception.MissingCharacterException;
import aitsi.m3spin.parser.exception.SimpleParserException;

import java.util.List;

public class CodeScanner {
    private CodePosition currentPosition;
    private List<String> code;

    CodeScanner(List<String> code) {
        this.code = code;
        this.currentPosition = new CodePosition();
    }

    CodePosition getCurrentPosition() {
        return currentPosition;
    }

    public List<String> getCode() {
        return code;
    }

    boolean hasCurrentChar() {
        return currentPosition.getLine() < code.size()
                && currentPosition.getColumn() < getCurrentLine().length();
    }

    char getCurrentChar() throws MissingCharacterException {
        if (hasCurrentChar()) return getCurrentLine().charAt(currentPosition.getColumn());
        else throw new MissingCharacterException(currentPosition);
    }

    boolean hasCurrentChar(char c) throws MissingCharacterException {
        return getCurrentChar() == c;
    }//brać pod uwagę koniec linii - przechodzic do kolejnej

    char getCurrentLetter() throws SimpleParserException {
        char currentChar = this.getCurrentChar();
        if (Character.isLetter(currentChar)) return currentChar;
        else throw new IllegalCharacterException(currentChar, this.currentPosition);
    }

    private void incrementPosition(int n) {
        if (currentPosition.getColumn() + n < getCurrentLine().length()) currentPosition.moveColumnBy(n);
        else {
            currentPosition.moveLine();
            incrementPosition(Math.max(currentPosition.getColumn() + n - getCurrentLine().length() - 1, 0));
        }
    }

    private String getCurrentLine() {
        return this.code.get(currentPosition.getLine());
    }

    void incrementPosition() {
        this.incrementPosition(1);
    }

    String getCurrentString(int length) throws MissingCharacterException {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(getCurrentChar());
            incrementPosition();
        }
        return String.valueOf(stringBuilder);
    }

    void skipWhitespaces() throws MissingCharacterException {
        if (Character.isWhitespace(getCurrentChar())) {
            incrementPosition();
            skipWhitespaces();
        }
    }
}
