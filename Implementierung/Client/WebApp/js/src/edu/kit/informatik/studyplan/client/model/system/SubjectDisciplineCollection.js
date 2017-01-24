goog.provide("edu.kit.informatik.studyplan.client.model.system.SubjectDisciplineCollection");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 */

edu.kit.informatik.studyplan.client.model.system.SubjectDisciplineCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.SubjectDisciplineCollection.prototype}*/{
    model : edu.kit.informatik.studyplan.client.model.system.SubjectDiscipline,
    parse : function (response, options) {
        var sub = [];
        for(var i = 0; i<response["subjects"].length; i++){
            sub.push(new this.model(response["subjects"][i],{parse : true}));
        }
        //console.log(sub);
        return sub;
    }
});