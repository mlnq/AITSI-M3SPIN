package aitsi.m3spin.parser;

public class CodePosition {
    private int line;
    private int column;

    public CodePosition() {
        this.line = 0;
        this.column = 0;
    }

    public CodePosition(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
