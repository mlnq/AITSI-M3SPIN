package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.pkb.interfaces.*;
import lombok.Getter;

@Getter
public class PkbImpl implements Pkb {
    Ast ast = new AstImpl();
    Follows followsMethods = new FollowsImpl();
    Modifies modifiesMethods = new ModifiesImpl();
    Parent parentMethods = new ParentImpl();
    Uses usesMethods = new UsesImpl();
}
