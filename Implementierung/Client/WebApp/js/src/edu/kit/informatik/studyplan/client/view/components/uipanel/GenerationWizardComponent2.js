goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2");
/**
 * @constructor
 * @param {Object=} options
 *@extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 * the second generation wizard: it adds preferences to modules which will be saved for the plan and used for generation.
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent2.prototype}*/{
    planId: null,
    //proposalInformation --> every generationWizard adds something.
    information: null,
    //implements the moduleFInder, which shows modules and filters and allows to set preferences.
    moduleFinder: null,
    
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/generationWizardComponent2.html"),
    // no events, because the ModuleFinder saves the changes.
    events: {
    },
    
    /**
    *constuctor: gets the planId and proposalInformation of GenerationWizardCompoinent1 and initialize a modulefinder which isn't draggable (here you don't want to insert modules in a plan), isnt shown as a sidebar (you don't need the current plan for that) but is preferencable (it's the purpose for that we use the moduleFinder)
    */
    initialize:
        function (options) {
            "use strict";
            this.planId = options.planId;
            this.information = options.information;
            this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
                isDraggable: false,
                isSidebar:false,
                isPreferencable: true,
                planId: this.planId
            });
        },

    
    /**
    *initialize a GenerationWizardComponent3 with the current proposalInformation and the planId of the plan which should be completed with the generation we actually prepare and returnes the component.
    *@return{edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
    */
    next:
        function () {
            "use strict";
            var temp = new edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent3({
                planId: this.planId,
                information: this.information
            });
            return temp;
        },
    
    /**
    *renders all included parts: just the moduleFinder and the general template
    */
    render:
        function () {
            "use strict";
            this.$el.html(this.template());
            var finder = this.$el.find(".modules");
            this.moduleFinder.render();
            this.delegateEvents();
            finder.append(this.moduleFinder.$el);
        },
    /**
    *not used, because the moduleFinder does all we need.
    */
    onChange:
        function () {
            "use strict";
        }

});