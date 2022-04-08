package aitsi.m3spin.parser;

import aitsi.m3spin.parser.exception.IllegalCharacterException;

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

    boolean hasNextChar() {
        return false;//todo zaimplementować: sprawdza czy na aktualnej pozycji jest znak
    }

    char getNextChar() {
        if (hasNextChar())
            return this.code.get(currentPosition.getLine()).charAt(currentPosition.getColumn());//todo brac pod uwage koniec linii
        return 0;//else //todo wyrzucic exception?
    }

    boolean hasNextChar(char c) {
        return false; //todo sprawdza, czy na aktualnej pozycji znajduje się znak c
    }

    char getNextLetter() throws IllegalCharacterException {
        char nextChar = this.getNextChar();
        if (Character.isLetter(nextChar)) return nextChar;
        else throw new IllegalCharacterException(nextChar, this.currentPosition);
    }

    private void incrementPosition(int n) {
        //todo zwiększa aktualną pozycję o N
    }

    void incrementPosition() {
        this.incrementPosition(1);
    }

    String getNextString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(getNextChar());
            incrementPosition();
        }
        return String.valueOf(stringBuilder);
    }

    void skipWhitespaces() {
        //todo przesuwa aktualną pozycję na najlbiższy niepusty znak (non-whitespace character)
    }
}
