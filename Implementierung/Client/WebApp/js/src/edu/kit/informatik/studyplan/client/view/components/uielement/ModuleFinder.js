goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/moduleFinder.html"),
    className: "moduleFinderWrapper",
    /**  @type {edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter} */
    moduleFilter: null,
    /**  @type {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList} */
    moduleList: null,
    events: {
        "change" : "",
        "click .filterButton": "refreshSearchCollection"
    },
    /**
    * parameters:
    *   isDraggable -> modules are draggable
    *   isPreferencable -> preference for modules can be set
    *
    * @this {Backbone.View}
    * @param {...*} options
    * @return *
    */
    initialize: function (options) {
        "use strict";
        this.planId = options.planId;
        //TODO: fetchModuleCollection
        this.moduleCollection = new edu.kit.informatik.studyplan.client.model.system.SearchCollection(null, {planId: this.planId});
        this.filterCollection = new edu.kit.informatik.studyplan.client.model.system.FilterCollection();
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        this.moduleCollection.setFilters(this.filterCollection);
        console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder] planId: "+this.planId);
        this.filterCollection.fetch({
            reset: true,
            success: function () {
                this.moduleCollection.fetch({
                    reset: true,
                    success: function () {
                        this.render();
                        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                    }.bind(this)
                });
            }.bind(this)
        });
        this.moduleList = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList( {               
                isRemovable: false,
                isDraggable: options.isDraggable,
                isPreferencable: options.isPreferencable,
                planId: this.planId,
                moduleCollection: this.moduleCollection
        });
        this.moduleFilter = new edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter( {
            filterCollection: this.filterCollection
        } );       
        
    },
    /**
    * Renders the ModuleFinder whereby 
    *   uiFilters will be put into .profileModuleFinderWrapper
    *   uiModuleCollection will be put into .profileModuleCollectionWrapper
    * @this {Backbone.View}
    * @return *    
    */
    render: function () {
        "use strict";
        this.$el.html(this.template());
        
        var filter = this.$el.find(".profileModuleFinderWrapper");
        this.moduleFilter.render();
        filter.prepend(this.moduleFilter.$el);
        
        var list = this.$el.find(".profileModuleCollectionWrapper");
        this.moduleList.render();
        list.prepend(this.moduleList.$el);
        
        this.delegateEvents();
    },
    /**
    * Reloads modules with applied filters
    */
    refreshSearchCollection: function () {
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        this.moduleCollection.fetch({
            reset: true,
            success: function () {
                edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
            }
        });
    }
});
