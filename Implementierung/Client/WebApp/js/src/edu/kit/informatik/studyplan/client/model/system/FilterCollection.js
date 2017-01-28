goog.provide("edu.kit.informatik.studyplan.client.model.system.FilterCollection");
var namespaceClient = edu.kit.informatik.studyplan.client;
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 */

edu.kit.informatik.studyplan.client.model.system.FilterCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.FilterCollection.prototype}*/{
    
    model : namespaceClient.model.system.Filter,
    
    parse : function (response, options) {
        "use strict";
        var filters = [];
        for (var i = 0; i < response.filters.length; i++) {
            var tmp = new this.model(response.filters[i], {parse : true, collection: this});
            filters.push(tmp);
        }
        return filters;
    },
    getParams : function () {
        "use strict";
        var result = {filter : ""};
        this.each(function (filter) {
            result.filter += (((result.filter !== "") ? "," : "") + filter.get('name'));
            _.extend(result,filter.getParams());
        });
        return result;
    }
});