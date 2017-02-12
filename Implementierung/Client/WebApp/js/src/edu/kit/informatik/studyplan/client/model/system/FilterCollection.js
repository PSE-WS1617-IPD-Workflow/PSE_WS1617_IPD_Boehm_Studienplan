goog.provide("edu.kit.informatik.studyplan.client.model.system.FilterCollection");
var namespaceClient = edu.kit.informatik.studyplan.client;
/**
 * @constructor
 * @param {Object=} response
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 */
edu.kit.informatik.studyplan.client.model.system.FilterCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.FilterCollection.prototype}*/{
    url: API_DOMAIN + "/filters",
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
        var result = {filters : ""};
        this.each(function (filter) {
            console.log(filter);
            result.filters += (((result.filters !== "") ? "," : "") + filter.get('name'));
            _.extend(result,filter.getParams());
        });
        return result;
    }
});