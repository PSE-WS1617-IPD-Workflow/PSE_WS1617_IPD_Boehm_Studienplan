goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1.prototype}*/{
    
    student: null,
    disciplines: null,
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
            this.disciplines = new edu.kit.informatik.studyplan.client.model.system.DisciplineCollection([
                {
                    id: 5,
                    name: "Aurorenausbildung"
                }
            ]);
                
            /*todo: kram hierdrunter wiedereinfügen anstelle der discipline hierdrüber.
            this.disciplines.fetch({
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
                disciplines: this.disciplines
            }));
            this.delegateEvents();
        },
    
    
    
    /**
    *@return{edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
    */
    next:
        function () {
            "use strict";
            console.log(this.student)
            var temp = new edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2({
                student: this.student,
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