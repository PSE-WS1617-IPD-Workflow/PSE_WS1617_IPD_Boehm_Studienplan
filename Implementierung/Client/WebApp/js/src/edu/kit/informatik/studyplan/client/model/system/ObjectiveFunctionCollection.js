goog.provide("edu.kit.informatik.studyplan.client.model.system.ObjectiveFunctionCollection");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 */

edu.kit.informatik.studyplan.client.model.system.ObjectiveFunctionCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.ObjectiveFunctionCollection.prototype}*/{
    url: API_DOMAIN + "/objective-functions",
    model : edu.kit.informatik.studyplan.client.model.system.ObjectiveFunction,
    parse : function (response, options) {
        "use strict";
        
        var objectiveFunctions = [];
        for(var i = 0; i<response["functions"].length; i++){
            objectiveFunctions.push(new this.model(response["functions"][i],{parse : true}));
        }
        return objectiveFunctions;
    }
});