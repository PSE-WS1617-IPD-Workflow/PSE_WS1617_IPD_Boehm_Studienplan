goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3");
/**
 * @constructor
 * @param {Object=} options
 *@extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3.prototype}*/{
    
    plan: null,
    information: null,
    
    fieldCollection: null,
    
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/generationWizardComponent3.html"),
    events: {
        //"change select.objectiveFunctionDropDown": "onChange"
    },
    
    /**
    *
    */ 
    initialize:
        function (options) {
            "use strict";
            this.plan = options.plan;
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
                                name: "Meistern von lebensgefährliche n Situationen"
                            }]
                }
            ]);
                
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
        
            $("#minECTS" + this.information.get('min-semester-ects')).val(ui.values[0]);
            $("#maxECTS" + this.filter.get('max-semester-ects')).val(ui.values[1]);
        },
     /**
    *
    */
    updateVal2:
        function (event, ui) {
            "use strict";
            var temp1 = ui.values[0];
            var temp2 = ui.values[1];
            this.information.set('min-semesters', temp1);
            this.information.set('max-semesters', temp2);
        
            $("#minSemester" + this.information.get('min-semesters')).val(ui.values[0]);
            $("#maxSemester" + this.filter.get('max-semesters')).val(ui.values[1]);
        },
        
              
    /**
    *
    */
    onChange:
        function () {
            "use strict";
        }

});