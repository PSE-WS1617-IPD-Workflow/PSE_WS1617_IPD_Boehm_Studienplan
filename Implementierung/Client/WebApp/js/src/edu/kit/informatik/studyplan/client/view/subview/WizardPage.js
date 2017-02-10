goog.provide("edu.kit.informatik.studyplan.client.view.subview.WizardPage");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.WizardPage = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.WizardPage.prototype} */{
    curView: null,
    onFinish: function () {},
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/subview/wizardPage.html"),
    events: {
        "click button.wizardPageNextWizard": "next"
    },
    
    /**
    *Konstruktor: Methode onFinish, welche Generierung auslößt und erstes Wizardfenster firstPage wird übergeben.
    * @this {Backbone.View}
    * @param {...*} options
    * @return *
    */
    initialize: function (options) {
        "use strict";
        this.curView = options.firstPage;
        this.onFinish = options.onFinish;
    },
    
    /**
    *
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
    *
    */
    next:
        function () {
            "use strict";
            var next = this.curView.next();
            if (next === null) {
                this.onFinish();
                // this.trigger("wizardComplete");
            } else {
                this.curView = next;
                this.render();
            }
        }
});