goog.provide("edu.kit.informatik.studyplan.client.view.subview.WizardPage");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 * the page that shows all wizards and opens the next one. Includes the "next"-button.
 */

edu.kit.informatik.studyplan.client.view.subview.WizardPage = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.WizardPage.prototype} */{
    /** the wizardComponent which is actually open.*/
    curView: null,
     /** the method, which works after finishing last wizard. */
    onFinish: function () {},
    /**
    * template of WizardPage.
    */
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/subview/wizardPage.html"),
    /** event for the "next"-button. */
    events: {
        "click button.wizardPageNextWizard": "next"
    },
    
    /**
    *Kcnstruktor: needed parameter: methode onFinish, which starts generation and first wizardComponent.
    * @this {Backbone.View}
    * @param {...*} options
    * @return *Generierung auslößt und erstes Wizardfenster firstPage wird übergeben.
    */
    initialize: function (options) {
        "use strict";
        this.curView = options.firstPage;
        this.onFinish = options.onFinish;
    },
    
    /**
    * builds template.
    */
    render:
        function () {
            "use strict";
            this.$el.html(this.template());
            var listDiv = this.$el.find(".wizardPageContent");
            this.curView.render();
            listDiv.append(this.curView.$el);
            this.delegateEvents();
            
        },
        
    /**
    *uses next-methode of current wizardComponent or starts onFinish, if its the last wizard. Renders new wizard.
    */
    next:
        function () {
            "use strict";
            var next = this.curView.next();
            if (next === null) {
                this.onFinish(this.curView);
            } else {
                this.curView = next;
                this.render();
            }
        }
});