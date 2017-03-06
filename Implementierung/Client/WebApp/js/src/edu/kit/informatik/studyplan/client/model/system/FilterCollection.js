goog.provide("edu.kit.informatik.studyplan.client.model.system.FilterCollection");
var namespaceClient = edu.kit.informatik.studyplan.client;
/**
 * @constructor
 * @param {Object=} response
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 * This class represents a collection of filters
 */
edu.kit.informatik.studyplan.client.model.system.FilterCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend( /** @lends {edu.kit.informatik.studyplan.client.model.system.FilterCollection.prototype}*/ {
    url: API_DOMAIN + "/filters",
    model: namespaceClient.model.system.Filter,

    parse: function (response, options) {
        "use strict";
        var filters = [];
        for (var i = 0; i < response.filters.length; i++) {
            var tmp = new this.model(response.filters[i], {
                parse: true,
                collection: this
            });
            filters.push(tmp);
        }
        return filters;
    },
    /**
     * Method for retrieving query parameters of a filter
     * @return {Object|null} containing the values of the query 
     */
    getParams: function () {
        "use strict";
        var result = {
            filters: ""
        };
        this.each(function (filter) {
            var params = filter.getParams();
            if (params !== null) {
                result.filters += (((result.filters !== "") ? "," : "") + filter.get('uri-name'));
                _.extend(result, params);
            }
        });
        return result;
    }
});