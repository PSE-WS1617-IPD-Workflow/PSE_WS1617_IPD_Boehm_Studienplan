goog.provide("edu.kit.informatik.studyplan.client.view.subview.PlanEditPage");
/**
 * @constructor
 * @extends {Backbone.View}
 * 
 */

edu.kit.informatik.studyplan.client.view.subview.PlanEditPage = Backbone.View.extend( /** @lends {edu.kit.informatik.studyplan.client.view.subview.PlanEditPage.prototype} */ {
    /**
     * filter and module view
     */
    moduleFinder: null,
    /**
     * view element to display current plan
     */
    planView: null,
    /**
     * view element to display options
     */
    planHeadBar: null,
    /**
     * view element to display options
     */
    tagName: "div",
    /**
     * @type {edu.kit.informatik.studyplan.client.model.plans.Plan}
     */
    model: null,
    /**
     * html template for this element
     */
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/subview/planEditPage.html"),
    /**
     * if this is true, the generated plan sidebar will be shown
     */
    proposed: false,
    /**
     * contains the view element moduleFinder
     **/
    standardModuleFinder: null,
    /**
     * dictates wheater it is possible to set a preference for modules
     */
    isPreferencable: true,
    /**
     *
     */

    /**
     * initializes the MainPage
     * @this {Backbone.View}
     * @param{...*} options
     *               parameters:
     *                   proposed -> should the proposal sidebar be shown
     *                   plan -> model element
     */
    initialize: function (options) {
        this.proposed = (typeof options.proposed) ? options.proposed : this.proposed;
        this.model = options.plan;
        if (!this.proposed) {
            //console.log("[edu.kit.informatik.studyplan.client.view.subview.PlanEditPage] planId")
            //console.log(this.model.get('id'));
            //moduleFinder should allow to drop some modules and put them in the plan, should allow to set preferences, is just a sidebar, because theres the plan too, has a planId.
            this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
                isSidebar: true,
                isPreferencable: true,
                isDraggable: true,
                isPlaced: this.model.containsModule,
                planId: this.model.get('id')
            })
        } else {
            //you cant change a generated plan. First you have to decide to save it (or delete).
            this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uipanel.ProposalSidebar({
                plan: this.model
            });
        }
        this.planView = new edu.kit.informatik.studyplan.client.view.components.uielement.Plan({
            isPreferencable: (!this.proposed),
            plan: this.model
        });
        //adding a HeadBar
        if (!this.proposed) {
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
    /**
     *renders all named parts: headbar, sidebar(moduleFinder), plan 
     */
    render: function () {
        this.$el.css({
            "min-width": this.$el.innerWidth()
        });
        this.$el.html(this.template({
            plan: this.model
        }));
        if (this.planHeadBar !== null) {
            this.planHeadBar.render();
            this.$el.find(".planEditHeadWrapper").append(this.planHeadBar.$el);
        }

        this.planView.render();
        this.$el.find(".planEditPlanWrapper").append(this.planView.$el);

        this.moduleFinder.render();
        this.$el.find(".planEditModuleFinderWrapper").append(this.moduleFinder.$el);
        this.$el.css({
            "min-width": "auto"
        });
    },
    /**
     *@param{edu.kit.informatik.studyplan.client.model.module.Module} module
     * load ModuleDetails and show them at the sidebar.  
     */
    showModuleDetails: function (module) {
        "use strict";
        //console.info("[edu.kit.informatik.studyplan.client.view.subview.PlanEditPage] showModuleDetails");
        if (this.standardModuleFinder === null) {
            this.standardModuleFinder = this.moduleFinder;
        }
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        module.fetch({
            success: function () {
                this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleInfoSidebar({
                    module: module,
                    isPreferencable: this.isPreferencable
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
     *hide ModuleDetails and shows last Sidebar.
     */
    hideModuleDetails: function () {
        "use strict";
        this.moduleFinder = this.standardModuleFinder;
        this.render();
    }
});