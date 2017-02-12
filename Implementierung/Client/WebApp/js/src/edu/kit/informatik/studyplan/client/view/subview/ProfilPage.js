 goog.provide("edu.kit.informatik.studyplan.client.view.subview.ProfilPage");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.subview.PlanEditPage}
 */

edu.kit.informatik.studyplan.client.view.subview.ProfilPage = edu.kit.informatik.studyplan.client.view.subview.PlanEditPage.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.ProfilPage.prototype} */{
    initialize: function (options) {
        // TODO: isSignUp
        this.isSignUp = options.isSignUp
        this.model = options.plan;
        this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
            isSidebar:true,
            isPreferencable:false,
            isDraggable: true,
            isPlaced:this.model.containsModule,
            planId: null
        })
        this.planView = new edu.kit.informatik.studyplan.client.view.components.uielement.Plan({
            plan: this.model,
            isPreferencable: false,
            isPassedPlan: true,
            isAddable: false            
        });
        if(!this.isSignUp){
            this.planHeadBar = new edu.kit.informatik.studyplan.client.view.components.uielement.ProfileHeadBar({
                plan: this.model
            });
        } else {
            this.planHeadBar = null;
        }
    },
    saveModules: function () {
        this.planHeadBar.savePlan(false);
    }
});
