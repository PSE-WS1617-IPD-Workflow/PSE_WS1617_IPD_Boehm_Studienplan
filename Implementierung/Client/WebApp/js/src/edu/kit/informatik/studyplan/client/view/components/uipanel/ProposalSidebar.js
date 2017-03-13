goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.ProposalSidebar");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.ProposalSidebar = Backbone.View.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.ProposalSidebar.prototype} */ {
    events: {
        "click .deleteProposal": "deletePlan",
        "click .saveProposal": "save",
        "click .saveAsProposal": "saveAs"
    },
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance()
        .getTemplate("resources/templates/components/uipanel/proposalSidebar.html"),
    initialize: function (options) {
        this.model = options.plan;
    },
    render: function () {
        this.$el.html(this.template());
    },
    /**
     * delete the plan
     */
    deletePlan: function () {
        "use strict";
        if(!confirm(edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("deleteProposal-sure"))){
            return;
        }
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate('/plans/' + this.model.get('id'), {
            trigger: true
        });
    },
    /**
     * saves the
     */
    save: function () {
        "use strict";
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        var plan = this.model.getPlan({
            newPlan: false,
            success: function () {
                // Make sure plan is verified
                plan.get('verificationResult').fetch({
                    success: function () {
                        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate('plans/' + plan.get('id'), {
                            trigger: true
                        });
                    }
                });
            }
        });
    },
    /**
     * saves propsal under a different name
     */
    saveAs: function () {
        "use strict";
        var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
        var name = prompt(LM.getMessage('newNameRequest'))
        if (name === null) {
            return false;
        }
        var plan = this.model.getPlan({
            newPlan: true,
            planName: name,
            success: function () {
                // Make sure plan is verified
                plan.get('verificationResult').fetch({
                    success: function () {
                        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate('plans/' + plan.get('id'), {
                            trigger: true
                        });
                    }
                });
            }
        });
    }

});