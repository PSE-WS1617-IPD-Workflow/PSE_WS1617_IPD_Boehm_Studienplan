goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2.prototype}*/{
    
    
    student: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/signUpWizardComponent2.html"),
    events: {
        "change select.objectiveFunctionDropDown": "onChange"
    },
    
    /**
    *
    */
    initialize:
        function (objects) {
            "use strict";
            this.student = objects.student;
            
        },
    
    /**
    *
    */
    render:
        function () {
            "use strict";
            this.$el.html(this.template());
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