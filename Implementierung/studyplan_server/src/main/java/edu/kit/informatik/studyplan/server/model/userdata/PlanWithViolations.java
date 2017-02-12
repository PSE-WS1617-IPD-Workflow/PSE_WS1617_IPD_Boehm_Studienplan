package edu.kit.informatik.studyplan.server.model.userdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.RuleGroup;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;

//TODO: javaDoc
public class PlanWithViolations extends Plan {
    @JsonProperty("violations")
    private List<ModuleConstraint> violations;

    @JsonProperty("field-violations")
    private List<Field> fieldViolations;

    @JsonProperty("rule-group-violations")
    private List<RuleGroup> ruleGroupViolations;

    public List<ModuleConstraint> getViolations() {
        return violations;
    }

    public void setViolations(List<ModuleConstraint> violations) {
        this.violations = violations;
    }

    public List<Field> getFieldViolations() {
        return fieldViolations;
    }

    public void setFieldViolations(List<Field> fieldViolations) {
        this.fieldViolations = fieldViolations;
    }

    public List<RuleGroup> getRuleGroupViolations() {
        return ruleGroupViolations;
    }

    public void setRuleGroupViolations(List<RuleGroup> ruleGroupViolations) {
        this.ruleGroupViolations = ruleGroupViolations;
    }
}
