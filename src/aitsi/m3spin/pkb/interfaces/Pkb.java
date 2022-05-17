package aitsi.m3spin.pkb.interfaces;

public interface Pkb {
    Ast getAst();
    Follows getFollowsMethods();
    Modifies getModifiesMethods();
    Parent getParentMethods();
    Uses getUsesMethods();
}
