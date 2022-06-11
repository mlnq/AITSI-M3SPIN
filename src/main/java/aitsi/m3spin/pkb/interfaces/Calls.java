package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Procedure;

import java.util.Set;

public interface Calls {

    /**
     * Adds fact: procedure calling calls called
     */
    void setCalls(Procedure calling, String called);

    /**
     * @return list of procedures which call calling passed as parameter directly
     */
    Set<String> getCalledBy(Procedure calling);

    /**
     * @return list of procedures which call calling passed as parameter both directly and indirectly
     */
    Set<Procedure> getCalledByT(Procedure calling);

    /**
     * @return list of procedures which are called directly from procedure passed as parameter
     */
    Set<Procedure> getCalling(Procedure called);

    /**
     * @return list of procedures which are called both directly and indirectly from procedure passed as parameter
     */
    Set<Procedure> getCallingT(Procedure called);

    /**
     * @return true if calling calls called directly
     */
    boolean isCalled(Procedure calling, Procedure called);

    /**
     * @return true if calling calls called either directly or indirectly
     */
    boolean isCalledT(Procedure calling, Procedure called);
}
