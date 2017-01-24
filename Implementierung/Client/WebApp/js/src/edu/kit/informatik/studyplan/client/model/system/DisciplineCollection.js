goog.provide("edu.kit.informatik.studyplan.client.model.system.DisciplineCollection");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 */

edu.kit.informatik.studyplan.client.model.system.DisciplineCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.DisciplineCollection.prototype}*/{
    model : edu.kit.informatik.studyplan.client.model.system.Discipline,
    parse : function (response, options) {
        "use strict";
        
        var disc = [];
        for(var i = 0; i<response["disciplines"].length; i++){
            disc.push(new this.model(response["disciplines"][i],{parse : true}));
        }
        return disc;
    }
});