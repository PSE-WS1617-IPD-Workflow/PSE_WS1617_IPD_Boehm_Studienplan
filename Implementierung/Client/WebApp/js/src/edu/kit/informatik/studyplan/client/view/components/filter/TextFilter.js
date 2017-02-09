goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.TextFilter");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent}
 */

edu.kit.informatik.studyplan.client.view.components.filter.TextFilter = edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.filter.TextFilter.prototype}*/{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/filter/textFilter.html"),
    /**
    *return String
    */
    getParams: function () {
        "use strict";
    },
    /**
    *
    */
    onSelect: function () {
        "use strict";
    }
});