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
    proposed: false,
    initialize: function (options) {
        this.proposed = (typeof options.proposed) ? options.proposed : this.proposed;
        this.model = options.plan;
        if(!this.proposed){
            this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
                isSidebar:true,
                isPreferencable:true,
                isDraggable: true,
                isPlaced:this.model.containsModule,
                planId: this.model.get('id')
            })
        } else {
            this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uipanel.ProposalSidebar({
                plan: this.model
            });
        }
        this.planView = new edu.kit.informatik.studyplan.client.view.components.uielement.Plan({
            plan: this.model
        });
        if(!this.proposed){
            this.planHeadBar = new edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar({
                plan: this.model
            });
        } else {
            this.planHeadBar = new edu.kit.informatik.studyplan.client.view.components.uielement.ProposedHeadBar({
                plan: this.model
            });
        }
    },
    render: function () {
        this.$el.html(this.template({
            plan: this.model
        }));
        if (this.planHeadBar!==null) {
            this.planHeadBar.render();
            this.$el.find(".planEditHeadWrapper").append(this.planHeadBar.$el);
        }
        
        this.planView.render();
        this.$el.find(".planEditPlanWrapper").append(this.planView.$el);
        
        this.moduleFinder.render();
        this.$el.find(".planEditModuleFinderWrapper").append(this.moduleFinder.$el);
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