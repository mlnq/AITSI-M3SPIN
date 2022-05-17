package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Procedure;

import java.util.List;

public interface Calls {

    /**
     * Adds fact: procedure procedure1 calls procedure2
     */
    void setCalls(Procedure procedure1, Procedure procedure2);

    /**
     * @return list of procedures which call procedure passed as parameter directly
     */
    List<Procedure> getCalledBy(Procedure procedure);

    /**
     * @return list of procedures which call procedure passed as parameter both directly and indirectly
     */
    List<Procedure> getCalledByT(Procedure procedure);

    /**
     * @return list of procedures which are called directly from procedure passed as parameter
     */
    List<Procedure> getCalledFrom(Procedure procedure);

    /**
     * @return list of procedures which are called both directly and indirectly from procedure passed as parameter
     */
    List<Procedure> getCalledFromT(Procedure p);

    /**
     * @return true if procedure1 calls procedure2 directly
     */
    Boolean isCalled(Procedure procedure1, Procedure procedure2);

    /**
     * @return true if procedure1 calls procedure2 either directly or indirectly
     */
    Boolean isCalledT(Procedure procedure1, Procedure procedure2);
}
