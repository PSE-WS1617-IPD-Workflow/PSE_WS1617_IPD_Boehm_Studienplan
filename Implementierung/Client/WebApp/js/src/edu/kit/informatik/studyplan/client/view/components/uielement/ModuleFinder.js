goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/moduleFinder.html"),
    moduleFilter: null,
    moduleList: null,
    events: {
        "click .filterButton": "refreshSearchCollection"
    },
    isSidebar: true,
    isPreferencable: true,
    isPlaced : null,
    initialize: function (options) {
        "use strict";
        this.moduleCollection = options.moduleCollection;
        this.isSidebar = options.isSidebar;
        this.isPreferencable = options.isPreferencable;
        this.isPlaced = options.isPlaced;
        
        //TODO: correct parameters
        this.moduleList = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList( { });
        this.moduleFilter = new edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter( { } );        
    },
    
    /**
    *
    */
    onSearch: function () {
        "use strict";
    },
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
    getFilter: function (){
        "use strict";
    },
    refreshSearchCollection: function () {
        //TODO: fetch has to be implemented properly
        
        this.moduleFilter.getSearchCollection().fetch();
    }
});