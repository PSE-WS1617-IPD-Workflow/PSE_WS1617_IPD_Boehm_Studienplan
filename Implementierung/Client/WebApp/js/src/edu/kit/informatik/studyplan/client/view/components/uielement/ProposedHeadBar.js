goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ProposeHeadBar");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar}
 * Class which represents the head bar in the profile view
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ProposedHeadBar = edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ProposedHeadBar.prototype}*/ {
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance()
        .getTemplate("resources/templates/components/uielement/proposedHeadBar.html"),
    events: {
        "click button.mainPageNavigation": "goHome"
    },
    initialize: function (options) {
        "use strict";
        this.model = options.plan;
    },
    /**
     * Method which leads the user back to the main page
     */
    goHome: function () {
        "use strict";
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/", {
            trigger: true
        });
    },
    render: function () {
        "use strict";
        this.$el.html(this.template({
            plan: this.model
        }));
        this.delegateEvents();
    }
});