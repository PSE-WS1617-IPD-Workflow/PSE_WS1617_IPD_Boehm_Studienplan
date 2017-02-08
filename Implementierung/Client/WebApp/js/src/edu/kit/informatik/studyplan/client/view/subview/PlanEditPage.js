goog.provide("edu.kit.informatik.studyplan.client.view.subview.PlanEditPage");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.PlanEditPage = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.PlanEditPage.prototype} */{
    moduleFinder: null,
    /**
     * @type {edu.kit.informatik.studyplan.client.model.plans.Plan}
     */
    model: null,
    initialize: function (options) {
        this.plan = options.plan;
        this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
            moduleCollection:new edu.kit.informatik.studyplan.client.model.system.SearchCollection(),
            isSidebar:true,
            isPreferencable:true,
            isPlaced:this.model.containsModule
        })
    },
    /**
    *@param{edu.kit.informatik.studyplan.client.model.module.Module} module
    */
    showModuleDetails:
        function (module) {
            "use strict";
        },
    /**
    *
    */
    hideModuleDetails:
        function () {
            "use strict";
        }
});