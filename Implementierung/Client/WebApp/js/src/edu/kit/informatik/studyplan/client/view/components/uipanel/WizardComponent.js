goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent");
/**
 * @constructor
 * @extends {Backbone.View}
 * parent class of all wizardComponents.
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent} */{
    /**
    *@return{edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
    * returnes next wizardComponent. Is it the last, it returns null.
    */
    next:
        function () {
            "use strict";
        }
});