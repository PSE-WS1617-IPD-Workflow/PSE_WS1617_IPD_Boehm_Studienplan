goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement");
/**
 * @constructor
 * @param {Object} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement.prototype} */{
    tagName: 'tr',
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/planListEl.html"),
    plan: null,
    initialize: function (options) {
        this.plan = options.plan;
        this.checkBox = $("<input type='checkbox'/>");
    },
    render: function () {
        this.$el.html(this.template({plan: this.plan}));
    },
    /**
    *
    */
    show: function () {
        "use strict";
        document.location.hash = "/plan/" + this.plan.get('id');
    },
    /**
    *
    */
    exportPlan: function () {
        "use strict";
    },
    /**
    *
    */
    duplicate: function () {
        "use strict";
    },
    /**
    *
    */
    deletePlan: function () {
        "use strict";
    }

});