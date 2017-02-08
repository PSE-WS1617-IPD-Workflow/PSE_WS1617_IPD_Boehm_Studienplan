goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.SelectFilter");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent}
 */

edu.kit.informatik.studyplan.client.view.components.filter.SelectFilter = edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.filter.SelectFilter.prototype}*/{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/SelectFilter.html"),
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
    }
});