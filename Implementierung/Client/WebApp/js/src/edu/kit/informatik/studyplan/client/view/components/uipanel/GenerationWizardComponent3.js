goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3");
/**
 * @constructor
 * @param {Object=} options
 *@extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3.prototype}*/{
    
    plan: null,
    information: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/generationWizardComponent3.html"),
    events: {
        //"change select.objectiveFunctionDropDown": "onChange"
    },
    
    /**
    *
    */
    initialize:
        function (options) {
            "use strict";
            this.plan = options.plan;
            this.information = options.information;
        },
    
    /**
     * @suppress {missingProperties}
     * @this {edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3}
     * @return {Backbone.View|null}
     */
    render:
        function () {
            "use strict";
            this.$el.html(this.template());
            this.$el.find(".rangeSlider1").slider({
                range: true,
                min: 0,
                max: 60,
                values: [0, 60],
                slide: this.updateVal//.bind(this)
            });
            this.$el.find(".rangeSlider2").slider({
                range: true,
                min: 2,
                max: 20,
                values: [2, 20],
                slide: this.updateVal//.bind(this)
            });
            this.delegateEvents();
            return this;
        },
    
    /**
    *@return{edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
    */
    next:
        function () {
            "use strict";
            return null;
        },
    /**
    *
    */
    onChange:
        function () {
            "use strict";
        }

});