goog.provide("edu.kit.informatik.studyplan.client.model.plans.Plan");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 */

edu.kit.informatik.studyplan.client.model.plans.Plan = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.Plan.prototype}*/{
    urlRoot : API_DOMAIN + "/plans",
    
    parse : function (response, options) {
        "use strict";
        // Check if plan modules were given in response
        if (typeof response.modules !== "undefined") {
            // Initialise an object of type client.model.plans.SemesterCollection and set planId and module
            // TODO: Inject modules from Student.getInstance().get('passedModules');
            response.semesterCollection = new edu.kit.informatik.studyplan.client.model.plans.SemesterCollection({planId : response.id, modules : response.modules}, {parse : true});
        }
        // Check if violations exist, if so add them
        if (typeof response.violations !== "undefined") {
            var violations = [];
            for (var i = 0; i < response.violations; i++) {
                violations.push(new edu.kit.informatik.studyplan.client.model.module.ModuleConstraint(response.violations[i],{parse:true}));
            }
            response.violations = violations;
        }
        return response;
    },
    /**
    * @param {number} moduleId
    * @return {boolean}
    */
    containsModule : function (moduleId) {
        "use strict";
    },
    /**
    *
    */
    loadVerification : function () {
        "use strict";
    }
});