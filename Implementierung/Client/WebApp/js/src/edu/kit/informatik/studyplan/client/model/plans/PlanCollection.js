goog.provide("edu.kit.informatik.studyplan.client.model.plans.PlanCollection");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 */

edu.kit.informatik.studyplan.client.model.plans.PlanCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.PlanCollection.prototype}*/{
    model : edu.kit.informatik.studyplan.client.model.plans.Plan,
    parse : function (response, options) {
        // Initialise plan array
        var plans = [];
        // For each plan in response: Create an object of type client.model.plans.Plan
        for(var i = 0; i<response["plans"].length; i++){
            plans.push(new this.model(response.plans[i],{parse : true}));
        }
        return plans;
    }
});