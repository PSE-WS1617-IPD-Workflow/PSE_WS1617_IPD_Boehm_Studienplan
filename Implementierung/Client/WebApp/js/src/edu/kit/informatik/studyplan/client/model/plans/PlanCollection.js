goog.provide("edu.kit.informatik.studyplan.client.model.plans.PlanCollection");
var clientPackage = edu.kit.informatik.studyplan.client;
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 */

edu.kit.informatik.studyplan.client.model.plans.PlanCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.PlanCollection.prototype}*/{
    model : clientPackage.model.plans.Plan,
    parse : function (response, options) {
        var plans = [];
        for(var i = 0; i<response.plans.length; i++){
            plans.push(new this.model({
                planId : response.plans[i].id,
                name : response.plans[i].name,
                verificationState : response.plans[i].status,
                creditpoints : response.plans[i]["creditpoints-sum"]
            }));
        }
    }
});