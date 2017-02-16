goog.provide("edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.module.ModuleCollection}
 * Collection of modules passed by the user
 */

edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection = edu.kit.informatik.studyplan.client.model.module.ModuleCollection.extend( /** @lends {edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection.prototype}*/ {
    url: API_DOMAIN + "/modules",
    /**
     * Method which converts these modules in a Plan (which is then displayable)
     */
    toPlan: function () {
        var passedPlan = new edu.kit.informatik.studyplan.client.model.plans.Plan({
            plan: {
                id: null,
                status: "not-verified",
                "creditpoints-sum": 0,
                name: "Bestanden",
                modules: []
            }
        }, {
            parse: true
        });
        passedPlan.url = function () {
            return "";
        }
        return passedPlan;
    }
});