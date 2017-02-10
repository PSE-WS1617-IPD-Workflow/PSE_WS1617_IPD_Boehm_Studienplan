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
        this.moduleList = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList( {               isRemovable: false,
                isDraggable: options.isDraggable,
                isPreferencable: options.isPreferencable
        });
        this.moduleFilter = new edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter( { } );        
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
    onChange: function (){
        this.render();
    },
    /**
    * Reloads modules with applied filters
    */
    refreshSearchCollection: function () {
        //TODO: fetch has to be implemented properly
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        this.moduleFilter.getSearchCollection().fetch({
            success: function () {
              edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
            }
        });
    }
});
