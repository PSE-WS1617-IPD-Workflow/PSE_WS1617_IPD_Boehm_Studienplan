goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/filter/rangeFilter.html"),
    tag: "ul",
    filterComponents: null,
    filterCollection: null,
    searchCollection: null,
    initialize: function (options){
        
        //TODO: enable fetch again
        //this.filterCollection = new edu.kit.informatik.studyplan.client.model.system.FilterCollection();
        //this.filterCollection.fetch();
        this.filterCollection = new edu.kit.informatik.studyplan.client.model.system.FilterCollection(   {                  filters : [{
                    id : 0,
                    name : "Test",
                    'default-value': {
                        min : 10,
                        max : 20
                    },
                    tooltip : "Test",
                    specification: {
                        type : "range",
                        min : 0,
                        max : 200
                    }
                }]}, {parse:true});
        
        this.searchCollection = new edu.kit.informatik.studyplan.client.model.system.SearchCollection(this.filterCollection);
        
        this.filterComponents = [];
        

        //foreach in filterCollection
        var _this = this;
        this.filterCollection.each(function (el) {
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
                _this.filterComponents.push(uiFilter);
            }
        });
    },
    render: function () {
        "use strict";
        this.$el.html(this.template());
        
        this.delegateEvents();
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