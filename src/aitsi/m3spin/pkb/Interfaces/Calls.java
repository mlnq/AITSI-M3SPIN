package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.interfaces.Procedure;

import java.util.List;

public interface Calls {
    //TODO impl: CallsTable[][]

    //Procedure p calls q
    void setCalls (Procedure p, Procedure q);

    //Which procedures call procedure q?
    List<Procedure> getCalledBy (Procedure q);

    //Which procedures are called from p?
    List<Procedure> getCalledFrom (Procedure p);

    //Does procedure p call q?
    Boolean isCalled (Procedure p, Procedure q);

//TODO co robimy z Calls*

//    //Which procedures call procedure q?
    List<Procedure> getCalls$ (Procedure q);
//
//    //Which procedures are called from p?
    List<Procedure> getCalled$From (Procedure p);
//
//    //Does procedure p call q?
    Boolean isCalls$ (Procedure p, Procedure q);
}
