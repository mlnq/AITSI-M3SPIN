package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
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
        Set<Procedure> procList = getCalledBy(calling);

        if (procList == null)
            return Collections.emptySet();
        Set<Procedure> resultProcedures = new HashSet<>(procList);
        for (Procedure proc : procList) {
            Set<Procedure> tmp = getCalledByT(proc);
            if (tmp != null)
                resultProcedures.addAll(tmp);
        }
        return resultProcedures;
    }

    @Override
    public Set<Procedure> getCalling(Procedure called) {
        HashSet<Procedure> calledFrom = new HashSet<>();
        for (Map.Entry<Procedure, HashSet<Procedure>> entry : calls.entrySet()) {
            if (entry.getValue().contains(called)) {
                calledFrom.add(entry.getKey());
            }
        }
        return calledFrom;
    }

    @Override
    public Set<Procedure> getCallingT(Procedure called) {
        Set<Procedure> procedures = new HashSet<>();
        Set<Procedure> proc = getCalling(called);
        if (proc.size() == 0)
            return Collections.emptySet();
        while (proc != null) {
            procedures.addAll(proc);
            proc = getCalling(called);
        }
        return procedures;
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
