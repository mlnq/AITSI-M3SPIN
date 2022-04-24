package aitsi.m3spin.spafrontend.parser;

public class CodePosition {
    private int line;
    private int column;

    CodePosition() {
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

    private void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    private void setColumn(int column) {
        this.column = column;
    }

    void moveColumnBy(int n) {
        this.setColumn(this.column + n);
    }

    private void moveLineBy(int n) {
        this.setLine(this.line + n);
        this.setColumn(0);
    }

    void moveLine() {
        this.moveLineBy(1);
    }
}
