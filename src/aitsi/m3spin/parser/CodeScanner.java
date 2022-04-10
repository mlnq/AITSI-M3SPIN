package aitsi.m3spin.parser;

import aitsi.m3spin.parser.exception.IllegalCharacterException;
import aitsi.m3spin.parser.exception.MissingCharacterException;
import aitsi.m3spin.parser.exception.SimpleParserException;

import java.util.List;

public class CodeScanner {
    private CodePosition currentPosition;
    private List<String> codeLines;

    CodeScanner(List<String> codeLines) {
        this.codeLines = codeLines;
        this.currentPosition = new CodePosition();
    }

    CodePosition getCurrentPosition() {
        return currentPosition;
    }

    public List<String> getCodeLines() {
        return codeLines;
    }

    boolean hasCurrentChar() {
        return currentPosition.getLine() < codeLines.size()
                && currentPosition.getColumn() < getCurrentLine().length();
    }

    char getCurrentChar() throws MissingCharacterException {
        if (hasCurrentChar()) return getCurrentLine().charAt(currentPosition.getColumn());
        else throw new MissingCharacterException(currentPosition);
    }

    boolean hasCurrentChar(char c) throws MissingCharacterException {
        return getCurrentChar() == c;
    }

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
        return this.codeLines.get(currentPosition.getLine());
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

    public char getCurrentDigit() throws SimpleParserException {
        char currentChar = this.getCurrentChar();
        if (Character.isDigit(currentChar)) return currentChar;
        else throw new IllegalCharacterException(currentChar, this.currentPosition);
    }
}
