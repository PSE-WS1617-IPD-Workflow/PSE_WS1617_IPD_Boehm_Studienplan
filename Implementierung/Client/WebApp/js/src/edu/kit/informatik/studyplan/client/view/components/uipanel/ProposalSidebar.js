goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.ProposalSidebar");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.ProposalSidebar = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.ProposalSidebar.prototype} */{
    events: {
        "click .deleteProposal" : "deletePlan",
        "click .saveProposal" : "save",
        "click .saveAsProposal" : "saveAs"
    },
    template:edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance()
        .getTemplate("resources/templates/components/uipanel/proposalSidebar.html"),
    initialize: function (options) {
        this.model=options.plan;
    },
    render: function () {
        this.$el.html(this.template());
    },
    /**
    *
    */
    deletePlan:
        function () {
            "use strict";
            edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate('/plans/'+this.model, {trigger: true});
        },
    /**
    *
    */
    save:
        function () {
            "use strict";
            edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
            var plan = this.model.getPlan({newPlan: false});
            plan.save(null,{
                success: function () {
                    edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                    edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate('plans/'+plan.get('id'),{trigger: true});
                }
            });
        },
    /**
    *
    */
    saveAs:
        function () {
            "use strict";
            var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
            var name = prompt(LM.getMessage('newNameRequest'))
            if(name!==null){
                return false;
            }
            var plan = this.model.getPlan({newPlan: false, planName: name});
            plan.save(null,{
                success: function () {
                    edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                    edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate('plans/'+plan.get('id'),{trigger: true});
                }
            });
        }

});