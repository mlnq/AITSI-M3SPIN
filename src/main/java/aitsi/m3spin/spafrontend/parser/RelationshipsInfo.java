package aitsi.m3spin.spafrontend.parser;

import aitsi.m3spin.commons.interfaces.Variable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
public class RelationshipsInfo {
    private final List<Variable> modifiedVariables = new ArrayList<>();
    private final List<Variable> usedVariables = new ArrayList<>();
    private final Set<String> calledProcedures = new HashSet<>();

    public RelationshipsInfo(RelationshipsInfo otherRelInfo) {
        this.addRelInfo(otherRelInfo);
    }

    public static RelationshipsInfo emptyInfo() {
        return new RelationshipsInfo();
    }

    public static RelationshipsInfo merge(RelationshipsInfo info1, RelationshipsInfo info2) {
        RelationshipsInfo mergedInfo = new RelationshipsInfo();
        mergedInfo.addAllModifiedVars(info1.getModifiedVariables());
        mergedInfo.addAllModifiedVars(info2.getModifiedVariables());

        mergedInfo.addAllUsedVars(info1.getUsedVariables());
        mergedInfo.addAllUsedVars(info2.getUsedVariables());

        mergedInfo.addAllCalledProcsNames(info1.getCalledProcedures());
        mergedInfo.addAllCalledProcsNames(info2.getCalledProcedures());
        return mergedInfo;
    }

    public void addRelInfo(RelationshipsInfo otherRelInfo) {
        this.addAllModifiedVars(otherRelInfo.getModifiedVariables());
        this.addAllUsedVars(otherRelInfo.getUsedVariables());
        this.addAllCalledProcsNames(otherRelInfo.getCalledProcedures());
    }

    public void addModifiedVar(Variable variable) {
        modifiedVariables.add(variable);
    }

    public void addAllModifiedVars(List<Variable> variables) {
        modifiedVariables.addAll(variables);
    }

    public void addUsedVar(Variable variable) {
        usedVariables.add(variable);
    }

    public void addAllUsedVars(List<Variable> variables) {
        usedVariables.addAll(variables);
    }

    public void addCalledProcName(String procName) {
        calledProcedures.add(procName);
    }

    public void addAllCalledProcsNames(Set<String> procNames) {
        calledProcedures.addAll(procNames);
    }

}
