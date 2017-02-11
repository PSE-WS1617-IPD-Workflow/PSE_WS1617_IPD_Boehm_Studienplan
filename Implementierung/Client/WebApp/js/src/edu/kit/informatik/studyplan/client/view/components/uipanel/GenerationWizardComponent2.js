goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2");
/**
 * @constructor
 * @param {Object=} options
 *@extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2.prototype}*/{
    plan: null,
    information: null,
    moduleFinder: null,
    
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/generationWizardComponent2.html"),
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
            this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
                isDraggable: false,
                isPreferencable: true
            });
        },

    
    /**
    *@return{edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
    */
    next:
        function () {
            "use strict";
            var temp = new edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3({
                plan: this.plan,
                information: this.information
            });
            return temp;
        },
    
    /**
    *
    */
    render:
        function () {
            "use strict";
            this.$el.html(this.template());
            var finder = this.$el.find(".modules");
            this.moduleFinder.render();
            this.delegateEvents();
            finder.append(this.moduleFinder.$el);
        },
    /**
    *
    */
    onChange:
        function () {
            "use strict";
        }

});