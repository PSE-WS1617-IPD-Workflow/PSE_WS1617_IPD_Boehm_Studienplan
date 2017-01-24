goog.provide("edu.kit.informatik.studyplan.client.model.system.SearchCollection");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.module.ModuleCollection}
 */

edu.kit.informatik.studyplan.client.model.system.SearchCollection = edu.kit.informatik.studyplan.client.model.module.ModuleCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.SearchCollection.prototype}*/{
    model : edu.kit.informatik.studyplan.client.model.module.ModuleCollection,
    /**
    * @param {edu.kit.informatik.studyplan.client.model.system.FilterCollection} filters
    */
    setFilters : function (filters) {
        "use strict";
        this.filters = filters;
    },
    
    parse :  function (response, options) {
        "use strict";
        var col = new this.model(response, {parse : true});        
        return col;
    }
});