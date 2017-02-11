goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/filter/moduleFilter.html"),
    tag: "ul",
    filterComponents: null,
    filterCollection: null,
    searchCollection: null,
    events: {
        "click .filterMenuButton": "showFilterSettings",
        "change .filterButton": "onChange"
    },
    initialize: function (options){
        
        //TODO: enable fetch again
        /*edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        this.filterCollection = new edu.kit.informatik.studyplan.client.model.system.FilterCollection();
        this.filterCollection.fetch({
            success: function () {
                edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
            }
        });*/
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
                }, {
                    id : 4,
                    name : "TestList",
                    'default-value': 1,
                    tooltip : "Test",
                    specification: {
                        type : "list",
                        items : [{
                            id: 1,
                            text:"a"
                        },{
                            id: 2,
                            text:"b"
                        }, {
                            id: 3,
                            text:"c"
                        }]
                    }
                },{
                    id : 2,
                    name : "TestContains",
                    'default-value': "testDefVal",
                    tooltip : "Test",
                    specification: {
                        type : "contains",
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
            switch(el.get("specification").type) {
                case "contains":
                    uiFilter = new edu.kit.informatik.studyplan.client.view.components.filter.TextFilter(el);
                    break;
                case "list":
                    uiFilter = new edu.kit.informatik.studyplan.client.view.components.filter.SelectFilter(el);
                    break;
                case "range":
                    uiFilter = new edu.kit.informatik.studyplan.client.view.components.filter.RangeFilter(el);
                    break;
            }
            if(uiFilter !== null) {
                uiFilter.initialize(tmp_options);
                _this.filterComponents.push(uiFilter);
            }
        });
    },
    /**
    * @this {Backbone.View}
    * @return *    
    */
    render: function () {
        "use strict";
        var filterButtons = [];
        for(var i = 0; i < this.filterComponents.length; i++){
            var tmpFilterComponent = this.filterComponents[i];
            filterButtons.push({ id: tmpFilterComponent.filter.get("id"),
                                name: tmpFilterComponent.filter.get("name")});
        }
        
        this.$el.html(this.template({
            buttons : filterButtons
        }));
        
        var finder = this.$el.find(".collectivefilterSettings");
        for(var i = 0; i < this.filterComponents.length; i++){
            var tmpFilterComponent = this.filterComponents[i];
            tmpFilterComponent.render();
            finder.append(tmpFilterComponent.$el);
        }
        this.delegateEvents();
    },
    
    /**
    *
    */
    onChange: function () {
        "use strict";
        this.render();
    },
    buildParam: function () {
    },
    getSearchCollection: function () {
        this.searchCollection.setFilters(this.filterCollection);
        return this.searchCollection;
    },
    showFilterSettings : function (event){
        console.log("[ModuleFilter] EVENTS:");
        console.log(event.target.id);
        $(".profileFilterWrapperSettings").hide();
        $("#filterId_" + event.target.id).show();
        
    }
});