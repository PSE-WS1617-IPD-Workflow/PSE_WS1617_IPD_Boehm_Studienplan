goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1.prototype}*/{
    
    beginningArray: [],
    student: null,
    disciplines: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/signUpWizardComponent1.html"),
    events: {
        "change select.beginningdateDropDown": "onChange1",
        "change select.disciplineDropDown": "onChange2"
    },
    date: null,
    
    
    /** 
    * 
    */
    initialize:
        function (options) {
            "use strict";
            this.date = new Date(Date.now()).getFullYear();
            this.beginning(this.date);
            console.log(this.date);
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
                disciplines: this.disciplines,
                beginningVal: this.beginningArray
            }));
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
    onChange1:
        function () {
            "use strict";
            
            this.student.set('studyStartYear', this.beginningArray[(this.$el.find("select.beginningdateDropDown").val())]['year']);
            this.student.set('studyStartCycle', this.beginningArray[this.$el.find("select.beginningdateDropDown").val()]['term']);
            
        },
    
    /**
    *
    */
    onChange2:
        function () {
            "use strict";
            this.student.set('discipline', this.disciplines.get(this.$el.find("select.disciplineDropDown").val()));            
        },
    
    /**
    *
    */
    beginning:
        function (curyear) {
            "use strict";
            for (var i = 0; i <= 10; i++){
                var temp = curyear-i;
                this.beginningArray.push({
                    name: (edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("winterterm")+ temp),
                    year: curyear-i,
                    term: "WT"
                });
                this.beginningArray.push({
                    name: (edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("summerterm")+ temp),
                    year: curyear-i,
                    term: "ST"
            });
                console.log(this.beginningArray[i]);
            }
        }

});