package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.pkb.interfaces.Calls;

import java.util.*;

public class CallsImpl implements Calls {
    HashMap<Procedure, HashSet<String>> calls = new HashMap<>();

    @Override
    public void setCalls(Procedure calling, String called) {
        if (calls.containsKey(calling)) {
            calls.get(calling).add(called);
        } else {
            calls.computeIfAbsent(calling, k -> new HashSet<>(Collections.singletonList(called)));
        }
    }

    @Override
    public Set<String> getCalledBy(Procedure calling) {
        HashSet<String> called = calls.get(calling);
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
        String procName = called.getProcName();
        HashSet<Procedure> calledFrom = new HashSet<>();
        for(Map.Entry<Procedure, HashSet<String>> entry: calls.entrySet()) {
            if(entry.getValue().contains(procName)) {
                calledFrom.add(entry.getKey());
            }
        }
        return calledFrom;
    }

    @Override
    public Set<Procedure> getCallingT(Procedure called) {
        return Collections.emptySet();
    }

    @Override
    public boolean isCalled(Procedure calling, Procedure called) {
        return getCalledBy(calling).contains(called.getProcName());
    }

    @Override
    public boolean isCalledT(Procedure calling, Procedure called) {
        return getCalledByT(calling).contains(called.getProcName());
    }
}
