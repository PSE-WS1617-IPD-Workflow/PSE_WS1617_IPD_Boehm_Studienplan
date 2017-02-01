goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList");
/**
 * @constructor
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
        this.planCollection.each(function (curPlan) {
            this.planListElements.push(new edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement({
                plan: curPlan
            }));
        });
    },
    render: function () {
        "use strict";
        this.$el.append($(this.template()));
        _.each(this.planListElements, function (el) {
            this.$("#planList").append(el.render());
        });
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