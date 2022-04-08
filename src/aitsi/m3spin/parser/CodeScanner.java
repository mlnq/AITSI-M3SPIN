package aitsi.m3spin.parser;

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
}
