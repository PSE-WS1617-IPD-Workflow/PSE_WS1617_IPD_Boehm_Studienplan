goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ProposeHeadBar");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ProposedHeadBar = edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ProposedHeadBar.prototype}*/{
    template:edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance()
        .getTemplate("resources/templates/components/uielement/proposedHeadBar.html"),
    initialize: function (options) {
        this.model = options.plan;
    },
    render: function () {
        this.$el.html(this.template({plan: this.model}));
    }
});