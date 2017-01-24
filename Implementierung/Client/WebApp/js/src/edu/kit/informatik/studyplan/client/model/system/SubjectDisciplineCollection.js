goog.provide("edu.kit.informatik.studyplan.client.model.system.SubjectDisciplineCollection");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 */

edu.kit.informatik.studyplan.client.model.system.SubjectDisciplineCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.SubjectDisciplineCollection.prototype}*/{
    model : edu.kit.informatik.studyplan.client.model.system.SubjectDiscipline,
    parse : function (response, options) {
        // Initialise plan array
        var sub = [];
        // For each plan in response: Create an object of type client.model.plans.Plan
        for(var i = 0; i<response["subjects"].length; i++){
            sub.push(new this.model(response.subjects[i],{parse : true}));
        }
        return sub;
    }
});