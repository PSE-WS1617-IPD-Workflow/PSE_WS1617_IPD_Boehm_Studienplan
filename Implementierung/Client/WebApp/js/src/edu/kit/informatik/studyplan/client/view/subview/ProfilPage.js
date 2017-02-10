goog.provide("edu.kit.informatik.studyplan.client.view.subview.ProfilPage");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.ProfilPage = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.ProfilPage.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/subview/profilPage.html"),
    
    moduleFinder: null,
    
    initialize: function (){
        this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
            isDraggable = true;
            isPreferencable = false;
        
        });
    },
    
    /**
    *
    */
    close: function () {
        "use strict";
    },
    /**
    *@param {edu.kit.informatik.studyplan.client.model.module.Module} module
    */
    showModuleDetails:
        function (module) {
            "use strict";
    },
    
    render: function () {
        "use strict";
        this.$el.html(this.template());
        var finder = this.$el.find(".profileEditModuleFinderWrapper");
        
        this.moduleFinder.render();
        finder.append(this.moduleFinder.$el);
        
        this.delegateEvents();
    },
    /**
    *
    */
    hideModuleDetails: function () {
            "use strict";
    }
});