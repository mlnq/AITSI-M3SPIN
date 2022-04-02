package aitsi.m3spin.pkb.Interfaces;
import aitsi.m3spin.commons.*;

import java.util.List;

public interface Calls {
    //TODO impl: CallsTable[][]

    //Procedure p calls q
    void setCalls (PROC p,PROC q);

    //Which procedures call procedure q?
    List<PROC> getCalledBy (PROC q);

    //Which procedures are called from p?
    List<PROC> getCalledFrom (PROC p);

    //Does procedure p call q?
    Boolean isCalled (PROC p,PROC q);

//TODO co robimy z Calls*

//    //Which procedures call procedure q?
    List<PROC> getCalls$ (PROC q);
//
//    //Which procedures are called from p?
    List<PROC> getCalled$From (PROC p);
//
//    //Does procedure p call q?
    Boolean isCalls$ (PROC p,PROC q);
}
