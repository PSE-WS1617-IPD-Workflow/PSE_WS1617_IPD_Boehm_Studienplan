goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder.prototype} */{
    /**  @type {edu.kit.informatik.studyplan.client.model.system.TemplateManager} */
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/moduleFinder.html"),
    /**  @type {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFilter} */
    moduleFilter: null,
    /**  @type {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList} */
    moduleList: null,
    events: {
        "click .filterButton": "refreshSearchCollection"
    },
    /**
    * parameters:
    *   isDraggable -> modules are draggable
    *   isPreferencable -> preference for modules can be set
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
        //TODO: fetch has to be implemented properly
        
        this.moduleFilter.getSearchCollection().fetch();
    }
});