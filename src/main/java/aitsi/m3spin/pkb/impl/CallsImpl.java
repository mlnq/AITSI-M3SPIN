package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.pkb.interfaces.Calls;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
    public Set<Procedure> getCalledBy(Procedure calling) {
        HashSet<Procedure> called = calls.get(calling);
        if (called == null) {
            return Collections.emptySet();
        } else {
            return new HashSet<>(called);
        }
    }

    @Override
    public Set<Procedure> getCalledByT(Procedure calling) {
        return Collections.emptySet();
    }

    @Override
    public Set<Procedure> getCalling(Procedure called) {
        return Collections.emptySet();
    }

    @Override
    public Set<Procedure> getCallingT(Procedure called) {
        return Collections.emptySet();
    }

    @Override
    public boolean isCalled(Procedure calling, Procedure called) {
        return getCalledBy(calling).contains(called);
    }

    @Override
    public boolean isCalledT(Procedure calling, Procedure called) {
        return getCalledByT(calling).contains(called);
    }
}
