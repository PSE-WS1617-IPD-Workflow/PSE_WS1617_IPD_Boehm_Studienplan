goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3");
/**
 * @constructor
 * @param {Object=} options
 *@extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3.prototype}*/{
    
    planId: null,
    information: null,
    
    fieldCollection: null,
    
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/generationWizardComponent3.html"),
    events: {
        "change select.fieldDropDown": "onChange"
    },
    
    /**
    *
    */ 
    initialize:
        function (options) {
            "use strict";
            this.planId = options.planId;
            this.information = options.information;
            this.information.set('min-semester-ects', 0);
            this.information.set('max-semester-ects', 60);
            this.information.set('min-semesters', 2);
            this.information.set('max-semesters', 20);
            
            this.fieldCollection = new edu.kit.informatik.studyplan.client.model.system.FieldCollection([
                {
                    id: 5,
                    name: "Ergänzungsfach",
                    'min-ects': 50,
                    categories: [{
                                id: 42,
                                name: "Meistern von lebensgefährlichen Situationen"
                            },
                                {
                                id: 41,
                                name: "blub"
                            }]
                }
            ]);
            this.information.set('fieldCollection',this.fieldCollection);
                
            /*todo: kram hierdrunter oder so ähnlich wiedereinfügen anstelle des objekts hierdrüber.
            this.fieldCollection.fetch({
                success: (function () {
                    this.render();
                }.bind(this))
            });*/
        },
    
    /**
     * @suppress {missingProperties}
     * @this {edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3}
     * @return {Backbone.View|null}
     */
    render:
        function () {
            "use strict";
            this.$el.html(this.template({
                curvalues: this.information,
                fields: this.fieldCollection
            }));
            this.$el.find(".rangeSlider1").slider({
                range: true,
                min: 0,
                max: 60,
                values: [0, 60],
                slide: this.updateVal1.bind(this)
            });
            this.$el.find(".rangeSlider2").slider({
                range: true,
                min: 2,
                max: 20,
                values: [2, 20],
                slide: this.updateVal2.bind(this)
            });
            this.delegateEvents();
            return this;
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
    updateVal1:
        function (event, ui) {
            "use strict";
            var temp1 = ui.values[0];
            var temp2 = ui.values[1];
            this.information.set('min-semester-ects', temp1);
            this.information.set('max-semester-ects', temp2);
        
            $("#minECTS").val(ui.values[0]);
            $("#maxECTS").val(ui.values[1]);
        },
     /**wizardPageContent
    *
    */
    updateVal2:
        function (event, ui) {
            "use strict";
            var temp1 = ui.values[0];
            var temp2 = ui.values[1];
            this.information.set('min-semesters', temp1);
            this.information.set('max-semesters', temp2);
        
            $("#minSemester").val(ui.values[0]);
            $("#maxSemester").val(ui.values[1]);
        },
        
              
    /**
    *
    */
    onChange:
        function (event) {
            "use strict";
            console.log("generation3")
            console.log(event);
            var fieldId = event.currentTarget.getAttribute("data-field-id");
            var value = event.currentTarget.value;            
            this.information.get("fieldCollection").get(fieldId).set("curValue", value);               
        }

});