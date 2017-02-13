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
    standardModuleFinder: null,
    initialize: function (options) {
        this.proposed = (typeof options.proposed) ? options.proposed : this.proposed;
        this.model = options.plan;
        if(!this.proposed){
            console.log("[edu.kit.informatik.studyplan.client.view.subview.PlanEditPage] planId")
            console.log(this.model.get('id'));
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
        edu.kit.informatik.studyplan.client.model.system.EventBus.on("showModuleInfo", this.showModuleDetails.bind(this));
        edu.kit.informatik.studyplan.client.model.system.EventBus.on("hideModuleInfo", this.hideModuleDetails.bind(this));
    },
    render: function () {
        this.$el.css({"min-width":this.$el.innerWidth()});
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
        this.$el.css({"min-width":"auto"});
    },
    /**
    *@param{edu.kit.informatik.studyplan.client.model.module.Module} module
    */
    showModuleDetails: function (module) {
        "use strict";
        console.info("[edu.kit.informatik.studyplan.client.view.subview.PlanEditPage] showModuleDetails");
        if (this.standardModuleFinder===null) {
            this.standardModuleFinder = this.moduleFinder;
        }
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        module.fetch({
            success: function () {
                this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleInfoSidebar({
                    module: module
                });
                this.render();
                edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
            }.bind(this),
            error: function () {
                this.standardModuleFinder = null;
            }.bind(this)
        });
    },
    /**
    *
    */
    hideModuleDetails: function () {
        "use strict";
        this.moduleFinder = this.standardModuleFinder;
        this.render();
    }
});