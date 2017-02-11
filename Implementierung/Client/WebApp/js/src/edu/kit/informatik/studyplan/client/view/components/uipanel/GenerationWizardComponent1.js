goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent1");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent1 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent1.prototype}*/{
    plan: null,
    information: null,
    objectiveFunctions: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/generationWizardComponent1.html"),
    events: {
        "change select.objectiveFunctionDropDown": "onChange"
    },
    
    
    /**
    *
    */
    initialize:
            function (options) {
            "use strict";
            this.plan = options.plan;
            this.information = options.information;
            this.objectiveFunctions = new edu.kit.informatik.studyplan.client.model.system.ObjectiveFunctionCollection([
                {
                    id: 5,
                    name: "möglichst schneller Studienabschluss"
                }
            ]);
                
            /*todo: kram hierdrunter wiedereinfügen anstelle des objekts hierdrüber.
            this.objectiveFunctions.fetch({
                success: (function () {
                    this.render();
                }.bind(this))
            });*/
        },
    /**
    *
    */
    render:
            function () {
            "use strict";
            this.$el.html(this.template({
                functions: this.objectiveFunctions
            }));
            this.delegateEvents();
        },
    /**
    *@return{edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
    */
    next:
        function () {
            "use strict";
            var temp = new edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2({
                plan: this.plan,
                information: this.information
            });            
            return temp;
        },
        
    /**
    *
    */
    onChange:
        function () {
            "use strict";
            var temp = this.objectiveFunctions.get(this.$el.find("select.objectiveFunctionDropDown").val());
            this.information.set('objectiveFunction', temp);
        }

});