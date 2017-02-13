package edu.kit.informatik.studyplan.server.model.userdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.RuleGroup;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.rest.resources.json.JsonModule;

/**
 * Only used for serialization of verification results.
 * Extends a Plan with violations attributes.
 */
public class PlanWithViolations {
    @JsonProperty("violations")
    private List<ModuleConstraint> violations;

    @JsonProperty("field-violations")
    private List<Field> fieldViolations;

    @JsonProperty("rule-group-violations")
    private List<RuleGroup> ruleGroupViolations;

    @JsonProperty("compulsory-violations")
    private List<JsonModule> compulsoryViolations;

    /**
     *
     * @return Module constraint violations
     */
    public List<ModuleConstraint> getViolations() {
        return violations;
    }

    /**
     * Sets module constraint violations
     * @param violations module constraint violations
     */
    public void setViolations(List<ModuleConstraint> violations) {
        this.violations = violations;
    }

    /**
     *
     * @return field violations
     */
    public List<Field> getFieldViolations() {
        return fieldViolations;
    }

    /**
     * Sets field violations
     * @param fieldViolations field violations
     */
    public void setFieldViolations(List<Field> fieldViolations) {
        this.fieldViolations = fieldViolations;
    }

    /**
     *
     * @return rule group violations
     */
    public List<RuleGroup> getRuleGroupViolations() {
        return ruleGroupViolations;
    }

    /**
     * Sets rule group violations
     * @param ruleGroupViolations rule group violations
     */
    public void setRuleGroupViolations(List<RuleGroup> ruleGroupViolations) {
        this.ruleGroupViolations = ruleGroupViolations;
    }

    /**
     *
     * @return missing compulsory modules violations
     */
    public List<JsonModule> getCompulsoryViolations() {
        return compulsoryViolations;
    }

    /**
     * Sets missing compulsory modules violations
     * @param compulsoryViolations compulsory violations
     */
    public void setCompulsoryViolations(List<JsonModule> compulsoryViolations) {
        this.compulsoryViolations = compulsoryViolations;
    }
    
}
