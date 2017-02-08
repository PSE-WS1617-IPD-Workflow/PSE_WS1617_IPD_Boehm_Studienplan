goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter.prototype} */{
    filterComponents: null,
    filterCollection: null,
    searchCollection: null,
    initialize: function (options){
        this.filterCollection = new edu.kit.informatik.studyplan.client.model.system.FilterCollection();
        this.filterCollection.fetch();
        
        this.searchCollection = new edu.kit.informatik.studyplan.client.model.system.SearchCollection(this.filterCollection);
        
        this.filterComponents = [];
        

        //foreach in filterCollection
        var el = "";
        
        var tmp_options = { filter: el};
        /**
         * @type {edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent}
         */
        var uiFilter = null;
        switch(el.type) {
            case "contains":
                uiFilter = new edu.kit.informatik.studyplan.client.view.components.filter.TextFilter();
                break;
            case "list":
                uiFilter = new edu.kit.informatik.studyplan.client.view.components.filter.SelectFilter();
                break;
            case "range":
                uiFilter = new edu.kit.informatik.studyplan.client.view.components.filter.RangeFilter();
                break;
        }
        if(uiFilter !== null) {
            uiFilter.initialize(tmp_options);
            this.filterComponents.push(uiFilter);
        }
    },
    
    /**
    *
    */
    onSearch: function () {
        "use strict";
    },
    
    /**
    * return FilterCollection
    */
    getFilters: function () {
        "use strict";
        return this.filterCollection;
    }
});