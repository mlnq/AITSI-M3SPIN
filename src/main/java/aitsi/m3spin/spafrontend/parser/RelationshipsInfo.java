package aitsi.m3spin.spafrontend.parser;

import aitsi.m3spin.commons.interfaces.Variable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RelationshipsInfo {
    private final List<Variable> modifiedVariables = new ArrayList<>();
    private final List<Variable> usedVariables = new ArrayList<>();

    public static RelationshipsInfo emptyInfo() {
        return new RelationshipsInfo();
    }

    public static RelationshipsInfo merge(RelationshipsInfo info1, RelationshipsInfo info2) {
        RelationshipsInfo mergedInfo = new RelationshipsInfo();
        mergedInfo.addAllModifiedVars(info1.getModifiedVariables());
        mergedInfo.addAllModifiedVars(info2.getModifiedVariables());

        mergedInfo.addAllUsedVars(info1.getUsedVariables());
        mergedInfo.addAllUsedVars(info2.getUsedVariables());
        return mergedInfo;
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
}
