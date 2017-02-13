goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent1");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent1 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent1.prototype}*/{
    planId: null,
    //proposalInformation --> every Component will add something.
    information: null,
    //collection, input with fetch
    objectiveFunctions: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/generationWizardComponent1.html"),
    //Dropdown to choose an objective function.
    events: {
        "change select.objectiveFunctionDropDown": "onChange"
    },
    
    
    /**
    *needs a proposalInformation and a plan. Gets the current ObjectiveFunctions from the server.
    */
    initialize:
            function (options) {
            "use strict";
            this.planId = options.planId;
            this.information = options.information;
            this.objectiveFunctions = new edu.kit.informatik.studyplan.client.model.system.ObjectiveFunctionCollection();
            this.objectiveFunctions.fetch({
                success: (function () {
                    this.render();
                }.bind(this))
            });
        },
    /**
    *renders the component.
    */
    render:
            function () {
            "use strict";
                //parameter: the ObjectiveFunctionsCollection, so the user can choose one in a dropdownmenue
            this.$el.html(this.template({
                functions: this.objectiveFunctions
            }));
            this.delegateEvents();
        },
    /**
    *@return{edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
    * returnes the next WizardComponent: GenerationWizardComponent2 with the current plan and all information, we getted at this (first) wizard: the objective function, which should used for generation.
    */
    next:
        function () {
            "use strict";
            var temp = new edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2({
                planId: this.planId,
                information: this.information
            });            
            return temp;
        },
        
    /**
    *saving the choosen objective function at the proposalInformation.
    */
    onChange:
        function () {
            "use strict";
            var temp = this.objectiveFunctions.get(this.$el.find("select.objectiveFunctionDropDown").val());
            this.information.set('objectiveFunction', temp);
        }

});