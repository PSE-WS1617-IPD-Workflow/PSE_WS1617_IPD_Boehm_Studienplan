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
    *
    */
    render:
        function () {
            "use strict";
            this.$el.html(this.template());
            this.$el.find(".rangeSlider1").slider({
                range: true,
                min: 0,
                max: 55,
                values: [0, 55],
                slide: this.updateVal//.bind(this)
            });
            this.$el.find(".rangeSlider2").slider({
                range: true,
                min: 5,
                max: 60,
                values: [5, 60],
                slide: this.updateVal//.bind(this)
            });
            this.$el.find(".rangeSlider3").slider({
                range: true,
                min: 2,
                max: 20,
                values: [2, 20],
                slide: this.updateVal//.bind(this)
            });
            this.$el.find(".rangeSlider4").slider({
                range: true,
                min: 2,
                max: 20,
                values: [2, 20],
                slide: this.updateVal//.bind(this)
            });
            this.delegateEvents();
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