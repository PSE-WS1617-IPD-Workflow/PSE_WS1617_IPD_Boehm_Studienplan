goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2.prototype}*/{
    
    
    student: null,
    moduleFinder: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/signUpWizardComponent2.html"),
    events: {
        "change select.objectiveFunctionDropDown": "onChange"
    },
    
    //Profilpage einfügen mit parameter isSignUp: true
    
    /**
    *
    */
    initialize:
        function (objects) {
            "use strict";
            this.student = objects.student;
            this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
                isDraggable: true,
                isPreferencable: false,
                planId: null
            });
        },
    
    /**
    *
    */
    render:
        function () {
            "use strict";
            this.$el.html(this.template());
            var finder = this.$el.find(".signUpWizardmodules");
            this.moduleFinder.render();
            finder.append(this.moduleFinder.$el);
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