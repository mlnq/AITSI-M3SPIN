package aitsi.m3spin.parser;

import aitsi.m3spin.parser.exception.IllegalCharacterException;

import java.util.List;

public class CodeScanner {
    private CodePosition currentPosition;
    private List<String> code;

    public CodeScanner(List<String> code) {
        this.code = code;
        this.currentPosition = new CodePosition();
    }

    public CodePosition getCurrentPosition() {
        return currentPosition;
    }

    public List<String> getCode() {
        return code;
    }

    public boolean hasNextChar() {
        return false;//todo zaimplementować: sprawdza czy na aktualnej pozycji jest znak
    }

    public char getNextChar() {
        return 0;//todo zwraca znak na aktualnej pozycji, lub wyjątek, jeśli nie ma już znaków
    }

    public boolean hasNextChar(char c) {
        return false; //todo sprawdza, czy na aktualnej pozycji znajduje się znak c
    }

    public char getNextLetter() throws IllegalCharacterException {
        char nextChar = this.getNextChar();
        if(Character.isLetter(nextChar)) return nextChar;
        else throw new IllegalCharacterException(nextChar, this.currentPosition);
    }

    public void incrementPosition(int n) {
        //todo zwiększa aktualną pozycję o N
    }

    public void incrementPosition() {
        this.incrementPosition(1);
    }

    public String getNextString(int length) {
        return null;//todo zwraca string o długości length zaczynając od aktualnej pozycji
    }

    public void skipWhitespaces() {
        //todo przesuwa aktualną pozycję na najlbiższy niepusty znak (non-whitespace character)
    }
}
