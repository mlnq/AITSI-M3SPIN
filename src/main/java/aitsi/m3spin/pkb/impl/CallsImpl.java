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
    public List<String> getCalledBy(Procedure procedure) {
        HashSet<String> called = calls.get(procedure);
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
        String procName = procedure.getProcName();
        HashSet<Procedure> calledFrom = new HashSet<>();
        for(Map.Entry<Procedure, HashSet<String>> entry: calls.entrySet()) {
            if(entry.getValue().contains(procName)) {
                calledFrom.add(entry.getKey());
            }
        }
        return new ArrayList<>(calledFrom);
    }

    @Override
    public List<Procedure> getCalledFromT(Procedure p) {
        return null;
    }

    @Override
    public Boolean isCalled(Procedure procedure1, Procedure procedure2) {
        return calls.get(procedure1).contains(procedure2.getProcName());
    }

    @Override
    public Boolean isCalledT(Procedure procedure1, Procedure procedure2) {
        return null;
    }
}
