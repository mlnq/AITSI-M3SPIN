package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.pkb.interfaces.Ast;
import lombok.Getter;

@Getter
public class Pkb {
    Ast ast = new AstImpl();
}
