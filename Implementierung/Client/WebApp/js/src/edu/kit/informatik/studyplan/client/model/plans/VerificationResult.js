goog.provide("edu.kit.informatik.studyplan.client.model.plans.VerificationResult");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 */

edu.kit.informatik.studyplan.client.model.plans.VerificationResult = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.VerificationResult.prototype}*/{
    url : function () {
        "use strict";
        return API_DOMAIN + "/plans/" + this.get('id') + '/verification';
    },
    parse : function (response, options) {
        "use strict";
        var result = response["plan"];
        if (typeof result["violations"] === "undefined") {
            result["violations"] = [];
        }
        var violations = [];
        for (var i = 0; i < result["violations"].length; i++) {
            violations.push(new edu.kit.informatik.studyplan.client.model.module.ModuleConstraint(result["violations"][i],{parse:true}));
        }
        result["violations"] = violations;
        return result;
    }
});