package aitsi.m3spin.spafrontend.parser;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class CodePosition {
    private int line = 0;
    private int column = 0;

    public CodePosition(CodePosition codePosition) {
        this.line = codePosition.getLine();
        this.column = codePosition.getColumn();
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
