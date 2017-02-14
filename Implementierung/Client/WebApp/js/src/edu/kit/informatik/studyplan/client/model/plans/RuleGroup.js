goog.provide("edu.kit.informatik.studyplan.client.model.plan.RuleGroup");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Model}
 * This class represents a rule group which might turn out to be violated during the verification process
 */

edu.kit.informatik.studyplan.client.model.plans.RuleGroup = Backbone.Model.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.RuleGroup.prototype}*/{
    parse : function (response, options) {
        "use strict";
        return response;
    }
});