goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 * the second SignUpWizward: collect signup information for the new profile.
 * saves passed modules
 * 
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2.prototype}*/{
    
    
    student: null,
    //implements the profilepage to insert passed modules. 
    profilPage: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/signUpWizardComponent2.html"),
    events: {
    },
    
    //Profilpage einfügen mit parameter isSignUp: true
    loaded: false,
    /**
    *constructor: initializes a profilePage with a empty plan.
    * and saves the student.
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
    *renders the template and the inserted profilepage
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
    *returnes null because its the last signup wizard
    *@return{edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
    */
    next:
        function () {
            "use strict";
            this.profilPage.saveModules();
            return null;
        },
    /**
    *no use, bechause profil page saves the passed modules.
    */
    onChange:
        function () {
            "use strict";
        }

});