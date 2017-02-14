goog.provide("edu.kit.informatik.studyplan.client.model.system.SearchCollection");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.module.ModuleCollection}
 * Collection which contains the modules which were searched by the user based on the filters in the set filter collection
 */

edu.kit.informatik.studyplan.client.model.system.SearchCollection = edu.kit.informatik.studyplan.client.model.module.ModuleCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.SearchCollection.prototype}*/{
    initialize : function (models, options) {
        "use strict";
        this.planId = options.planId
        edu.kit.informatik.studyplan.client.model.module.ModuleCollection.prototype.initialize.apply(this, arguments);
    },
    url : function () {
        "use strict";
        if (typeof this.planId !== "undefined" && this.planId!==null) {
            return API_DOMAIN + "/plans/" + this.planId + "/modules";
        } else {
            return API_DOMAIN + "/modules";
        }
    },
    /**
    * Method to set the used filter collection
    * @param {edu.kit.informatik.studyplan.client.model.system.FilterCollection} filters
    */
    setFilters : function (filters) {
        "use strict";
        this.filters = filters;
    },
    fetch : function (options) {
        "use strict";
        if (typeof options !== "object") {
            options = {};
        }
        _.extend(options, {data : this.filters.getParams()});
        return edu.kit.informatik.studyplan.client.model.module.ModuleCollection.prototype.fetch.apply(this, [options]);
    }
});