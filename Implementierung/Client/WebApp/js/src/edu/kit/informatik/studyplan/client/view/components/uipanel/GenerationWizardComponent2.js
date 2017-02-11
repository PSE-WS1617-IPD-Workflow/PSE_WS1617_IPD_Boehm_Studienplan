goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2");
/**
 * @constructor
 * @param {Object=} options
 *@extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2.prototype}*/{
    planId: null,
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
            this.planId = options.planId;
            this.information = options.information;
            this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
                isDraggable: false,
                isSidebar:false,
                isPreferencable: true,
                planId: this.planId
            });
        },

    
    /**
    *@return{edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
    */
    next:
        function () {
            "use strict";
            var temp = new edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3({
                planId: this.planId,
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