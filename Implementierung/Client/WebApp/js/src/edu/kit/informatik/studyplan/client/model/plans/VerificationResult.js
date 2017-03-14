goog.provide("edu.kit.informatik.studyplan.client.model.plans.VerificationResult");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 * Represents the verification result of a plan
 */

edu.kit.informatik.studyplan.client.model.plans.VerificationResult = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend( /** @lends {edu.kit.informatik.studyplan.client.model.plans.VerificationResult.prototype}*/ {
    /**
     * Key by which error messages are identified
     */
    modelErrorKey: "plans-verification",
    plan: null,
    initialize: function (attributes, options) {
        this.plan = options.plan;
        this.listenTo(this, "change", this.onChange.bind(this));
    },
    /**
     * Transfer the "change" event to the plan
     */
    onChange: function () {
        this.plan.trigger("change");
    },
    url: function () {
        "use strict";
        return API_DOMAIN + "/plans/" + this.get('id') + '/verification';
    },
    parse: function (response, options) {
        "use strict";
        var result = response["plan"];
        if (typeof result["violations"] === "undefined") {
            result["violations"] = [];
        }
        var violations = [];
        for (var i = 0; i < result["violations"].length; i++) {
            violations.push(new edu.kit.informatik.studyplan.client.model.module.ModuleConstraint(result["violations"][i], {
                parse: true
            }));
        }
        var fieldViolations = [];
        if (typeof result["field-violations"] !== "undefined") {
            for (var i = 0; i < result["field-violations"].length; i++) {
                fieldViolations.push(new edu.kit.informatik.studyplan.client.model.system.Field(
                    {
                        field: result["field-violations"][i]
                    }, {
                        parse: true
                    }));
            }
        }
        var ruleGroupViolations = [];
        if (typeof result["rule-group-violations"] !== "undefined") {
            for (var i = 0; i < result["rule-group-violations"].length; i++) {
                ruleGroupViolations.push(new edu.kit.informatik.studyplan.client.model.plans.RuleGroup(
                    result["rule-group-violations"][i], {
                        parse: true
                    }));
            }
        }
        var compulsoryViolations = [];
        if (typeof result["compulsory-violations"] !== "undefined") {
            for (var i = 0; i < result["compulsory-violations"].length; i++) {
                compulsoryViolations.push(new edu.kit.informatik.studyplan.client.model.module.Module({
                    module: {
                        id: result["compulsory-violations"][i]["id"],
                        name: result["compulsory-violations"][i]["name"]
                    }
                }, {
                    parse: true
                }));
            }
        }
        result["field-violations"] = fieldViolations;
        result["rule-group-violations"] = ruleGroupViolations;
        result["compulsory-violations"] = compulsoryViolations;
        result["violations"] = violations;
        return result;
    }
});