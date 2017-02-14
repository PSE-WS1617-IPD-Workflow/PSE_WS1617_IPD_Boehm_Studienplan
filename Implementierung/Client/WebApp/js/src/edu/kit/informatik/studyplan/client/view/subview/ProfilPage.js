 goog.provide("edu.kit.informatik.studyplan.client.view.subview.ProfilPage");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.subview.PlanEditPage}
 */

edu.kit.informatik.studyplan.client.view.subview.ProfilPage = edu.kit.informatik.studyplan.client.view.subview.PlanEditPage.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.ProfilPage.prototype} */{
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
        this.isSignUp = (typeof options.isSignUp !== "undefined")? options.isSignUp : false;
        this.model = options.plan;
        //Initialize moduleFinder
        this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
            isSidebar:true,
            isPreferencable:false,
            isDraggable: true,
            isPlaced:this.model.containsModule,
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
        if(this.isSignUp){
            this.planHeadBar.render = function () {
                // do nothing
                return null;
            }
            this.planHeadBar.$el.css({display:"none"});
        }
    },
    /**
    * Saves passed modules
    */
    saveModules: function () {
        this.planHeadBar.savePlan(false);
    }
});
