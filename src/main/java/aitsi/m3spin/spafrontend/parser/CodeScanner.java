package aitsi.m3spin.spafrontend.parser;

import aitsi.m3spin.commons.exception.*;
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

    private char parseChar(char c, boolean incFlag) throws MissingCharacterException { //todo ATS-11
        this.skipWhitespaces();

        if (this.hasCurrentChar(c)) {
            if (incFlag) this.incrementPosition();
            return c;
        } else throw new MissingCharacterException(c, this.getCurrentPosition());
    }

    public char parseChar(char c) throws MissingCharacterException {//todo ATS-11
        return parseChar(c, true);
    }

    public boolean hasCurrentChar() {
        if (currentPosition.getLine() < codeLines.size()) {
            if (currentPosition.getColumn() < getCurrentLine().length()) {
                return true;
            }
            currentPosition.moveLine();
            return hasCurrentChar();
        } else return false;
    }


    public boolean hasNextChar(int positionIncrement) {//todo czy potrzebne
        CodePosition oldPos = new CodePosition(currentPosition);
        incrementPosition(positionIncrement);

        boolean hasNextChar = currentPosition.getLine() + positionIncrement < codeLines.size()
                && currentPosition.getColumn() < getCurrentLine().length();

        currentPosition = oldPos;
        return hasNextChar;
    }

    public char getCurrentChar() throws MissingCharacterException {
        if (hasCurrentChar()) return getCurrentLine().charAt(currentPosition.getColumn());
        else throw new MissingCharacterException(currentPosition);
    }

    public boolean hasCurrentChar(char c) throws MissingCharacterException {
        if (!hasCurrentChar()) return false;
        try {
            return getCurrentChar() == c;
        } catch (MissingCharacterException e) {
            throw new MissingCharacterException(c, currentPosition);
        }
    }

    public char getCurrentLetter() throws CodeScannerException {
        char currentChar;
        try {
            currentChar = this.getCurrentChar();
        } catch (MissingCharacterException mce) {
            throw new MissingCharacterException("letter", currentPosition);
        }
        if (Character.isLetter(currentChar)) return currentChar;
        else throw new IllegalCharacterException(currentChar, this.currentPosition, "letter");
    }

    public void incrementPosition(int n) {
        if (currentPosition.getColumn() + n < getCurrentLine().length()) currentPosition.moveColumnBy(n);

        else {
            if (currentPosition.getLine() >= codeLines.size() - 1) {
                currentPosition.moveColumnBy(1);
                return;
            }
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

    public void parseKeyword(String keyword) throws MissingCodeEntityException {
        String keywordStr = getCurrentString(keyword.length());
        if (!keyword.equals(keywordStr)) {
            throw new MissingKeywordException(keyword, getCurrentPosition());
        }
        skipWhitespaces();
    }

    public String getCurrentString(int length) throws MissingCharacterException {
        return getCurrentString(length, true);
    }

    public boolean hasCurrentString(int length) throws MissingCharacterException {
        return hasCurrentString(length, null);
    }

    public boolean hasCurrentString(String searchedString) throws MissingCharacterException {
        return hasCurrentString(searchedString.length(), searchedString);
    }

    private boolean hasCurrentString(int length, String searchedString) throws MissingCharacterException {
        CodePosition oldPosition = new CodePosition(this.currentPosition);

        for (int i = 0; i < length; i++) {
            if (hasCurrentChar() && (searchedString == null || getCurrentChar() == searchedString.charAt(i))) {
                incrementPosition();
            } else {
                currentPosition = oldPosition;
                return false;
            }
        }
        currentPosition = oldPosition;
        return true;
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
        if (hasCurrentChar() && Character.isWhitespace(getCurrentChar())) {
            incrementPosition();
            skipWhitespaces();
        }
    }

    public char getCurrentDigit() throws CodeScannerException {
        char currentChar = this.getCurrentChar();
        if (Character.isDigit(currentChar)) return currentChar;
        else throw new IllegalCharacterException(currentChar, this.currentPosition, "digit");
    }


}
