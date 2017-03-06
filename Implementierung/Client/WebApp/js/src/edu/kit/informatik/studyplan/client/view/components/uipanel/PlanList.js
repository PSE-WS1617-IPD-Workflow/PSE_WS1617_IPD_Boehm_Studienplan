goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList = Backbone.View.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList.prototype} */ {
    tagName: 'div',
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/planList.html"),
    planCollection: null,
    planListElements: [],
    events: {
        "change #checkAllCheckbox": "checkAllCheckboxChange",
        "click button.planListActionExecution": "onActionSelection"
    },
    /**
     * initializes this class
     * @this {Backbone.View}
     * @param{...*} options
     *               planCollection -> connection to the model
     */
    initialize: function (options) {
        "use strict";
        this.planCollection = options.planCollection;
        this.planListElements = [];
        this.listenTo(this.planCollection, "change", this.reload);
        this.listenTo(this.planCollection, "add", this.reload);
        this.listenTo(this.planCollection, "reset", this.reload);
        this.listenTo(this.planCollection, "all", this.reload);
        this.reload();
    },
    /**
     * updates date and rerenders ui
     */
    reload: function () {
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList] reload");
        this.planListElements = [];
        var self = this;
        this.planCollection.each(function (curPlan) {
            self.planListElements.push(new edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement({
                plan: curPlan
            }));
        });
        this.render();
    },
    /**
     *renders the template and the inserted profilepage
     */
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
     * updates data after an action is selected
     */
    onActionSelection: function () {
        "use strict";
        var select = this.$el.find("select.planListAction");
        var planListEls = [];
        _.each(this.planListElements, function (curEl) {
            if (curEl.isChecked()) {
                planListEls.push(curEl);
            }
        })
        this[select.val()](planListEls);
    },
    /**
    * loads page to compare plans
    * @param{...*} planListEls
                    list of plans
    */
    comparePlans: function (planListEls) {
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList] comparePlans");
        //console.log(planListEls);
        if (planListEls.length !== 2) {
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
            "compare/" + planListEls[0].plan.get('id') + "/" + planListEls[1].plan.get('id'), {
                trigger: true
            }
        )
    },
    /**
    * sends a deleterequest for a plan
    * @param{Object|null} planListEls
                    list of plans
    */
    deletePlans: function (planListEls) {
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList] deletePlans");
        //console.log(planListEls);
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
    /**
     * updates all checkboxxes according to the select all box
     */
    checkAllCheckboxChange: function () {
        var isChecked = this.$el.find("#checkAllCheckbox").prop('checked');
        //console.log(this.planListElements);
        _.each(this.planListElements, function (el) {
            el.setChecked(isChecked);
        });
    }
});