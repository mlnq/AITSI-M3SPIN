package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.pkb.interfaces.*;
import lombok.Getter;

@Getter
public class Pkb {
    private final Calls callsInterface = new CallsImpl();
    private final Parent parentInterface = new ParentImpl();
    private final Follows followsInterface = new FollowsImpl();
    private final Modifies modifiesInterface = new ModifiesImpl();
    private final Uses usesInterface = new UsesImpl();
    private final Ast ast = new AstImpl();
}
