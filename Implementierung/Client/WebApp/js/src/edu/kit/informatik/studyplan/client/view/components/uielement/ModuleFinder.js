goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder.prototype} */{
    moduleFilter: null,
    moduleCollection: null,
    
    isSidebar: true,
    isPreferencable: true,
    isPlaced : null,
    initalize: function (options) {
        "use strict";
        this.moduleCollection = options.moduleCollection;
        this.isSidebar = options.isSidebar;
        this.isPreferencable = options.isPreferencable;
        this.isPlaced = options.isPlaced;
        
        //TODO: correct parameters
        this.moduleFilter = new edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter( {} );        
    },
    
    /**
    *
    */
    onSearch: function () {
        "use strict";
    },
    render: function () {
        "use strict";
    },
    getFilter: function (){
        "use strict";
    }
});