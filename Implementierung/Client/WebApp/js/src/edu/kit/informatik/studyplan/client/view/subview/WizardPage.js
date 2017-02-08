goog.provide("edu.kit.informatik.studyplan.client.view.subview.WizardPage");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.WizardPage = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.WizardPage.prototype} */{
    curView: null,
    onFinish: function () {},
    
    initialize: function (options) {
        "use strict";
        this.curView = options.firstPage;//new edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent();
   //     this.planList = new edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList({
     //       planCollection: this.planCollection
        this.onFinish = options.onFinish;
        //});
    },
    /**
    *
    */
    next:
        function () {
            "use strict";
            
            var next = this.curView.next();
            if (next === null) {
                this.onFinish();
                this.trigger("wizardComplete");
            } else {
                this.curView = next;
                this.render();
            }
        }
});