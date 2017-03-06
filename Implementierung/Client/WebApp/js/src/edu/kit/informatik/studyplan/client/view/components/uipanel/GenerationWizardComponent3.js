goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 * the third GenerationWizard allows to set the prefered ECTS per semester and prefered number of semesters.
 * it shows also some more options you can choose, which be loaded from the server (the fields).
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3.prototype}*/ {

    planId: null,
    //proposalInformation --> every generationWizard adds something.
    information: null,
    //the options the user can choose which are saved on the saver and will be dynamicly loaded.
    fieldCollection: null,

    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/generationWizardComponent3.html"),
    //the triggered event, if a field option is changed by the user.
    events: {
        "change select.fieldDropDown": "onChange"
    },

    /**
     *constuctor: gets planId and proposedInformation from wizardComponent2
     * sets some standard-werte to avoid errors because of undefined values.
     * initialize a fieldcollection and fetches the content from the server. 
     */
    initialize: function (options) {
        "use strict";
        this.planId = options.planId;
        this.information = options.information;
        this.information.set('min-semester-ects', 0);
        this.information.set('max-semester-ects', 60);
        this.information.set('min-semesters', 2);
        this.information.set('max-semesters', 20);

        this.fieldCollection = new edu.kit.informatik.studyplan.client.model.system.FieldCollection();
        this.fieldCollection.fetch({
            success: function () {
                this.render();
            }.bind(this)
        });
        this.information.set('fieldCollection', this.fieldCollection);

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
     * renders the sliders to choose ects per semester and preferred semester, the fields and general template.
     */
    render: function () {
        "use strict";
        this.$el.html(this.template({
            //template gets the fields and the proposalInformation for updating the slider and the information 
            //and to get the fields dynamicly
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
     * returnes null, because it's the last wizardComponent.
     */
    next: function () {
        "use strict";
        $("select.fieldDropDown").trigger("change");
        return null;
    },
    /**
     *updating and saving the slider for ects per semester
     */
    updateVal1: function (event, ui) {
        "use strict";
        var temp1 = ui.values[0];
        var temp2 = ui.values[1];
        this.information.set('min-semester-ects', temp1);
        this.information.set('max-semester-ects', temp2);

        $("#minECTS").val(ui.values[0]);
        $("#maxECTS").val(ui.values[1]);
    },
    /**
     *updating and saving the slider for preferredd number of semesters
     */
    updateVal2: function (event, ui) {
        "use strict";
        var temp1 = ui.values[0];
        var temp2 = ui.values[1];
        this.information.set('min-semesters', temp1);
        this.information.set('max-semesters', temp2);

        $("#minSemester").val(ui.values[0]);
        $("#maxSemester").val(ui.values[1]);
    },


    /**
     *that method will be triggered if one of the field-drowndown-menues will be changed.
     * the value will be saved in the fieldcollection of the current  proposalInformation.
     */
    onChange: function (event) {
        "use strict";
        //console.log("generation3")
        //console.log(event);
        var fieldId = event.currentTarget.getAttribute("data-field-id");
        var value = event.currentTarget.value;
        this.information.get("fieldCollection").get(fieldId).set("curValue", value);
    }

});