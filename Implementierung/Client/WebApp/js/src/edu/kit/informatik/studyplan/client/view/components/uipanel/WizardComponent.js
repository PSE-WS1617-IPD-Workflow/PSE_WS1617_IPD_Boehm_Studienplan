goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent");
/**
 * @constructor
 * @extends {Backbone.View}
 * @abstract
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent} */{
    /**
    *@abstract
    *@return{WizardComponent}
    */
    next:
        function () {
            "use strict";
        }
});