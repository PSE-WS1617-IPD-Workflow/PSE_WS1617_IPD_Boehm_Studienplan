goog.provide("edu.kit.informatik.studyplan.client.model.system.FilterCollection");
var namespaceClient = edu.kit.informatik.studyplan.client;
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 */

edu.kit.informatik.studyplan.client.model.system.FilterCollection = Backbone.Model.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.FilterCollection.prototype}*/{
    
    model : namespaceClient.model.system.Filter,
    
    parse : function (response, options) {
        "use strict";
        var filters = [];
        for (var i = 0; i<response.filters.length; i++){
            var tmp = new this.model(response.filters[i], {parse : true});
            filters.push(tmp);
            //console.log(tmp);
        }
        return filters;
    }
});