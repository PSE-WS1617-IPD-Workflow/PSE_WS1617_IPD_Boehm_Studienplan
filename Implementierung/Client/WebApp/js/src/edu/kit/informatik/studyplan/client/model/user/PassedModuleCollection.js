goog.provide("edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.module.ModuleCollection}
 */

edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection = edu.kit.informatik.studyplan.client.model.module.ModuleCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection.prototype}*/{
    url: API_DOMAIN + "/modules",
    
    toPlan: function(){
        var passedPlan = new edu.kit.informatik.studyplan.client.model.plans.Plan({
            plan: {
                id: null,
                status: "not-verified",
                "creditpoints-sum": 0,
                name:"Bestanden",
                modules: []
            }
        }, {parse: true});
        passedPlan.url = function () { return ""; }
        return passedPlan;
    }
});
