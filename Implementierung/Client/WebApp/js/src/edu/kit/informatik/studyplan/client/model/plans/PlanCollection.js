goog.provide("edu.kit.informatik.studyplan.client.model.plans.PlanCollection");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 * Collection of plans --> plans of one student.
 */

edu.kit.informatik.studyplan.client.model.plans.PlanCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend( /** @lends {edu.kit.informatik.studyplan.client.model.plans.PlanCollection.prototype}*/ {
    url: API_DOMAIN + "/plans",
    model: edu.kit.informatik.studyplan.client.model.plans.Plan,

    /**
     *parses all included plans.
     */
    parse: function (response, options) {
        // Initialise plan array
        var plans = [];
        // For each plan in response: Create an object of type client.model.plans.Plan
        for (var i = 0; i < response["plans"].length; i++) {
            plans.push(new this.model({
                plan: response.plans[i]
            }, {
                parse: true
            }));
        }
        return plans;
    }
});