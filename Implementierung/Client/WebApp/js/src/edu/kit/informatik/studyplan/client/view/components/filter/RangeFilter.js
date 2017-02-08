goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.RangeFilter");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent}
 */

edu.kit.informatik.studyplan.client.view.components.filter.RangeFilter = edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.filter.RangeFilter.prototype}*/{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/rangeFilter.html"),
    tag: "ul",
    /**
    *return String
    */
    getParams:
        function () {
            "use strict";
        },
    /**
    *
    */
    onSelect: function () {
        "use strict";
    },
    render: function () {
        this.$el.html(this.template({plan: this.plan}));
        this.delegateEvents();
    },
});