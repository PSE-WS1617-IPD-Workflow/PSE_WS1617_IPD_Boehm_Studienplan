goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1.prototype}*/{
    
    student: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/signUpWizardComponent1.html"),
    events: {
        "change select.objectiveFunctionDropDown": "onChange"
    },
    
    
    /** 
    * 
    */
    initialize:
        function (options) {
            "use strict";
            this.student = options.student;
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
            var temp = new edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2({
                student: this.student
            });
            return temp;
        },
    /**
    *
    */
    onChange:
        function () {
            "use strict";
        }

});