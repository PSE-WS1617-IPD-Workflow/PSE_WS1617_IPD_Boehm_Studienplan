goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar = edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar.prototype}*/{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/RegularHeadBar.html"),
    model: null,
    events: {
        "change #curPlanName": "rename",
        "click #generatePlan": "generate",
        "click #verifyPlan": "verify"
    },
    initialize: function (options) {
        this.model = options.plan;
        this.listenTo(this.model, "change", this.render);
    },
    render: function () {
        this.$el.html(this.template({plan: this.model}));
        this.delegateEvents();
    },
    /**
    *
    */
    generate: function () {
        "use strict";
        console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar] generate")
    },
    /**
    *
    */
    verify: function () {
        "use strict";
        console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar] verify")
    },
    /**
    *
    */
    rename: function () {
        "use strict";
        console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar] rename")
    }
});