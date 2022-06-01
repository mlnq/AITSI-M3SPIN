package aitsi.m3spin.spafrontend.parser;

import aitsi.m3spin.commons.exception.CodeScannerException;
import aitsi.m3spin.commons.exception.IllegalCharacterException;
import aitsi.m3spin.commons.exception.MissingCharacterException;
import lombok.Getter;

import java.util.List;

@Getter
public class CodeScanner {
    private final List<String> codeLines;
    private CodePosition currentPosition;

    public CodeScanner(List<String> codeLines) {
        this.codeLines = codeLines;
        this.currentPosition = new CodePosition();
    }

    public boolean hasCurrentChar() {
        return currentPosition.getLine() < codeLines.size()
                && currentPosition.getColumn() < getCurrentLine().length();
    }

    public char getCurrentChar() throws MissingCharacterException {
        if (hasCurrentChar()) return getCurrentLine().charAt(currentPosition.getColumn());
        else throw new MissingCharacterException(currentPosition);
    }

    public boolean hasCurrentChar(char c) throws MissingCharacterException {
        return getCurrentChar() == c;
    }

    public char getCurrentLetter() throws CodeScannerException {
        char currentChar = this.getCurrentChar();
        if (Character.isLetter(currentChar)) return currentChar;
        else throw new IllegalCharacterException(currentChar, this.currentPosition);
    }

    public void incrementPosition(int n) {
        if (currentPosition.getColumn() + n < getCurrentLine().length()) currentPosition.moveColumnBy(n);

        else {
            if (currentPosition.getLine() >= codeLines.size() - 1) return;
            currentPosition.moveLine();
            incrementPosition(Math.max(currentPosition.getColumn() + n - getCurrentLine().length() - 1, 0));
        }
    }

    private String getCurrentLine() {
        return this.codeLines.get(currentPosition.getLine());
    }

    public void incrementPosition() {
        this.incrementPosition(1);
    }

    public String getCurrentString(int length) throws MissingCharacterException {
        return getCurrentString(length, true);
    }

    public String getCurrentString(int length, boolean increment) throws MissingCharacterException {
        StringBuilder stringBuilder = new StringBuilder(length);
        CodePosition oldPosition = new CodePosition(this.currentPosition);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(getCurrentChar());
            incrementPosition();
        }
        if (!increment) {
            this.currentPosition = oldPosition;
        }
        return String.valueOf(stringBuilder);
    }

    public void skipWhitespaces() throws MissingCharacterException {
        if (Character.isWhitespace(getCurrentChar())) {
            incrementPosition();
            skipWhitespaces();
        }
    }

    public void skipLine() {
        currentPosition.setLine(currentPosition.getLine() + 1);
        currentPosition.setColumn(0);
    }

    public char getCurrentDigit() throws CodeScannerException {
        char currentChar = this.getCurrentChar();
        if (Character.isDigit(currentChar)) return currentChar;
        else throw new IllegalCharacterException(currentChar, this.currentPosition);
    }
}
