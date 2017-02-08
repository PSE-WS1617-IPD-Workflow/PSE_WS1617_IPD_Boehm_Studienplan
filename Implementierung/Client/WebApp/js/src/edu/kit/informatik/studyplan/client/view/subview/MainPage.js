goog.provide("edu.kit.informatik.studyplan.client.view.subview.MainPage");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.MainPage = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.MainPage.prototype} */{
    planCollection: null,
    tagName: "div",
    planList: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/subview/mainPage.html"),
    events: {
        "click button.mainPageAddPlan": "addPlan"
    },
    initialize: function (options) {
        "use strict";
        this.planCollection = options.planCollection;
        this.planList = new edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList({
            planCollection: this.planCollection
        });
    },
    render: function () {
        "use strict";
        this.$el.html(this.template());
        var listDiv = this.$el.find(".mainPagePlanList");
        this.planList.render();
        listDiv.append(this.planList.$el);
        this.delegateEvents();
    },
    /**
    *
    */
    addPlan:
        function () {
            "use strict";
            var planName = prompt(edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("planNameQuestion"), "");
            if (planName===null){
                return;
            }
            var newPlan = new edu.kit.informatik.studyplan.client.model.plans.Plan({
                name: planName
            }, {collection: this.planCollection});
            var router = edu.kit.informatik.studyplan.client.router.MainRouter.getInstance();
            newPlan.save({
                success: function () {
                    router.hideLoading();
                    router.navigate("plans/"+newPlan.get('id'),{trigger:true});
                }
            });
            router.showLoading();
        }
});