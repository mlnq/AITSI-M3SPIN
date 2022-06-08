package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.pkb.interfaces.Calls;

import java.util.*;

public class CallsImpl implements Calls {
    HashMap<Procedure, HashSet<Procedure>> calls = new HashMap<>();

    @Override
    public void setCalls(Procedure calling, Procedure called) {
        if (calls.containsKey(calling)) {
            calls.get(calling).add(called);
        } else {
            calls.computeIfAbsent(calling, k -> new HashSet<>(Collections.singletonList(called)));
        }
    }

    @Override
    public List<Procedure> getCalledBy(Procedure procedure) {
        HashSet<Procedure> called = calls.get(procedure);
        if (called == null) {
            return null;
        } else {
            return new ArrayList<>(called);
        }
    }

    @Override
    public List<Procedure> getCalledByT(Procedure procedure) {
        return null;
    }

    @Override
    public List<Procedure> getCalledFrom(Procedure procedure) {
        return null;
    }

    @Override
    public List<Procedure> getCalledFromT(Procedure p) {
        return null;
    }

    @Override
    public Boolean isCalled(Procedure procedure1, Procedure procedure2) {
        return null;
    }

    @Override
    public Boolean isCalledT(Procedure procedure1, Procedure procedure2) {
        return null;
    }
}
