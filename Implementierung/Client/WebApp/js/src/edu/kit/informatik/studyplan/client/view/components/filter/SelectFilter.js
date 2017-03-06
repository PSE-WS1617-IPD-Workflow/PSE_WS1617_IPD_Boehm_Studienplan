goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.SelectFilter");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent}
 */

edu.kit.informatik.studyplan.client.view.components.filter.SelectFilter = edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.filter.SelectFilter.prototype}*/ {
    /**
     * html template for this element
     */
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/filter/selectFilter.html"),
    /**
     * events triggered by HTML
     */
    events: {
        "change select.selectFilterDropDown": "dropDownChange"
    },
    /**
     * updates the model with data from HTML
     */
    dropDownChange: function () {
        this.filter.set("curValue", this.$el.find(".selectFilterDropDown").val());

    }
});