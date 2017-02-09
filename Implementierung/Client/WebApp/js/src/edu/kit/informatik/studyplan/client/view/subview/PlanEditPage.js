goog.provide("edu.kit.informatik.studyplan.client.view.subview.PlanEditPage");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.PlanEditPage = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.PlanEditPage.prototype} */{
    moduleFinder: null,
    planView: null,
    planHeadBar: null,
    tagName: "div",
    /**
     * @type {edu.kit.informatik.studyplan.client.model.plans.Plan}
     */
    model: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/subview/planEditPage.html"),
    initialize: function (options) {
        this.model = options.plan;
        this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
            moduleCollection:new edu.kit.informatik.studyplan.client.model.system.SearchCollection(),
            isSidebar:true,
            isPreferencable:true,
            isPlaced:this.model.containsModule
        })
        this.planView = new edu.kit.informatik.studyplan.client.view.components.uielement.Plan({
            plan: this.model
        });
        this.planHeadBar = new edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar({
            plan: this.model
        });
    },
    render: function () {
        this.$el.html(this.template({
            plan: this.model
        }));
        this.planHeadBar.render();
        this.$el.find(".planEditHeadWrapper").append(this.planHeadBar.$el);
        
        this.planView.render();
        this.$el.find(".planEditPlanWrapper").append(this.planView.$el);
    },
    /**
    *@param{edu.kit.informatik.studyplan.client.model.module.Module} module
    */
    showModuleDetails: function (module) {
        "use strict";
    },
    /**
    *
    */
    hideModuleDetails: function () {
        "use strict";
    }
});