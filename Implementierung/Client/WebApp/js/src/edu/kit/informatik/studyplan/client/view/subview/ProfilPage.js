 goog.provide("edu.kit.informatik.studyplan.client.view.subview.ProfilPage");
 /**
  * @constructor
  * @param {Object=} options
  * @extends {edu.kit.informatik.studyplan.client.view.subview.PlanEditPage}
  */

 edu.kit.informatik.studyplan.client.view.subview.ProfilPage = edu.kit.informatik.studyplan.client.view.subview.PlanEditPage.extend( /** @lends {edu.kit.informatik.studyplan.client.view.subview.ProfilPage.prototype} */ {
    pageTour: edu.kit.informatik.studyplan.client.model.system.TourManager.getInstance().getTour("profilePage"),
     /**
      * says wheather planHeadBar should be displayed
      */
     isSignUp: false,
     model: null,

     /**
      * the main element of the page -> to add passed Modules
      */
     planView: null,
     /*
      * headbar containing navigation options
      */
     planHeadBar: null,
     /*
      * modulerFinder that allows to search for modules
      */
     moduleFinder: null,
     isPreferencable: false,

     /**
      * Initializes the Plan edit page
      * possible Parameters are:
      *   isSignUp: will be set if this page is inside the signupwizzard,
      *   plan: the plan that should displayed -> passed modules,
      *   
      * @this {Backbone.View}
      * @param{...*} options
      */
     initialize: function (options) {
         // TODO: isSignUp
         this.isSignUp = (typeof options.isSignUp !== "undefined") ? options.isSignUp : false;
         this.model = options.plan;
         //Initialize moduleFinder
         this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
             isSidebar: true,
             isPreferencable: false,
             isDraggable: true,
             isPlaced: this.model.containsModule,
             planId: null
         })
         //Initialize PlanUI-Element
         this.planView = new edu.kit.informatik.studyplan.client.view.components.uielement.Plan({
             plan: this.model,
             isPreferencable: false,
             isPassedPlan: true,
             isAddable: false
         });

         //Initialize HeadBar
         this.planHeadBar = new edu.kit.informatik.studyplan.client.view.components.uielement.ProfileHeadBar({
             plan: this.model
         });
         if (this.isSignUp) {
             this.planHeadBar.render = function () {
                 // do nothing
                 return null;
             }
             this.planHeadBar.$el.css({
                 display: "none"
             });
         }
        edu.kit.informatik.studyplan.client.model.system.EventBus.on("showModuleInfo", this.showModuleDetails.bind(this));
        edu.kit.informatik.studyplan.client.model.system.EventBus.on("hideModuleInfo", this.hideModuleDetails.bind(this));
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
                    isPreferencable: false
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
    },
     /**
      * Saves passed modules
      */
     saveModules: function () {
         this.planHeadBar.savePlan(false);
     }
 });
