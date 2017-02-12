goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2.prototype}*/{
    
    
    student: null,
    profilPage: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/signUpWizardComponent2.html"),
    events: {
        "change select.objectiveFunctionDropDown": "onChange"
    },
    
    //Profilpage einfügen mit parameter isSignUp: true
    loaded: false,
    /**
    *
    */
    initialize:
        function (objects) {
            "use strict";
            this.student = objects.student;
            edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
            this.student.save(null,{
                success: function () {
                    this.profilPage = new edu.kit.informatik.studyplan.client.view.subview.ProfilPage({
                        plan: this.student.get('passedModules').toPlan(),
                        isSignUp: true

                    });
                    this.isLoaded = true;
                    this.render();
                    //edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                }.bind(this)
            });
            /**this.student.getDistance({
                success:
                    function () {
                        this.render();
                        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                        
                    }.bind(this)
            });*/
        },
    
    /**
    *
    */
    render:
        function () {
            "use strict";
            if (this.isLoaded) {
                this.$el.html(this.template());
                var finder = this.$el.find(".signUpWizardmodules");
                this.profilPage.render();
                finder.append(this.profilPage.$el);
                this.delegateEvents();
            }
        },
    /**
    *@return{edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
    */
    next:
        function () {
            "use strict";
            this.profilPage.saveModules();
            return null;
        },
    /**
    *
    */
    onChange:
        function () {
            "use strict";
        }

});