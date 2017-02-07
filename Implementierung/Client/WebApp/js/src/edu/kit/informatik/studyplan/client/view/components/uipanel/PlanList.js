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
    
    initialize: function (options) {
        "use strict";
        this.planCollection = options.planCollection;
        this.planListElements = [];
        var self = this;
        this.planCollection.each(function (curPlan) {
            self.planListElements.push(new edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement({
                plan: curPlan
            }));
        });
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
    },
    /**
    *
    */
    onChange: function () {
        "use strict";
    }
});