goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList.prototype} */{
    tagName: 'div',
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/planList.html"),
    planCollection: null,
    planListElements: [],
    events: {
        "change #checkAllCheckbox": "checkAllCheckboxChange",
        "click button.planListActionExecution": "onActionSelection"
    },
    initialize: function (options) {
        "use strict";
        this.planCollection = options.planCollection;
        this.planListElements = [];
        var self = this;
        this.listenTo(this.planCollection, "change", this.reload);
        this.listenTo(this.planCollection, "add", this.reload);
        this.listenTo(this.planCollection, "reset", this.reload);
        this.listenTo(this.planCollection, "all", this.reload);
        this.reload();
    },
    reload: function () {
        console.log("[edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList] reload");
        this.planListElements = [];
        var self = this;
        this.planCollection.each(function (curPlan) {
            self.planListElements.push(new edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement({
                plan: curPlan
            }));
        });
        this.render();
    },
    render: function () {
        "use strict";
        this.$el.html(this.template());
        var self = this;
        _.each(this.planListElements, function (el) {
            el.render();
            self.$("#planList").append(el.el);
        });
        this.delegateEvents();
    },
    /**
    *
    */
    onActionSelection: function () {
        "use strict";
        var select = this.$el.find("select.planListAction");
        var planListEls = [];
        _.each(this.planListElements, function (curEl) {
            if (curEl.isChecked()){
                planListEls.push(curEl);
            }
        })
        this[select.val()](planListEls);
    },
    comparePlans: function (planListEls) {
        console.log("[edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList] comparePlans");
        console.log(planListEls);
        if(planListEls.length!==2){
            edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance().add(
                new edu.kit.informatik.studyplan.client.model.system.Notification({
                    title: LM.getMessage("only2PlansComparableTitle"),
                    text: LM.getMessage("only2PlansComparableText"),
                    wasShown: false,
                    type: "error"
                })
            );
            return;
        }
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate(
            "compare/"+planListEls[0].plan.get('id')+"/"+planListEls[1].plan.get('id'),
            {trigger: true}
        )
    },
    deletePlans: function (planListEls) {
        console.log("[edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList] deletePlans");
        console.log(planListEls);
        _.each(planListEls, function (el) {
            el.deletePlan();
        });
    },
    /**
    *
    */
    onChange: function () {
        "use strict";
    },
    checkAllCheckboxChange: function () {
        var isChecked = this.$el.find("#checkAllCheckbox").prop('checked');
        console.log(this.planListElements);
        _.each(this.planListElements,function (el) {
            el.setChecked(isChecked);
        });
    }
});