goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar = edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar.prototype}*/{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/regularHeadBar.html"),
    model: null,
    events: {
        "change #curPlanName": "rename",
        "click #generatePlan": "generate",
        "click #verifyPlan": "verify",
        "click button.mainPageNavigation": "goHome"
    },
    initialize: function (options) {
        "use strict";
        this.model = options.plan;
        this.listenTo(this.model, "change", this.render);
    },
    render: function () {
        "use strict";
        this.$el.html(this.template({plan: this.model}));
        this.delegateEvents();
    },
    /**
    *
    */
    generate: function () {
        "use strict";
        console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar] generate");
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("plans/"+this.model.get('id')+"/generate", {trigger: true});
    },
    goHome: function () {
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/", {trigger: true})
    },
    /**
    *
    */
    verify: function () {
        "use strict";
        console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar] verify");
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        var self = this;
        var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
        this.model.get('verificationResult').fetch({
            success: function () {
                edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                var notification = null;
                if(self.model.get('verificationResult').get('status')=="valid"){
                    notification = new edu.kit.informatik.studyplan.client.model.system.Notification({
                        title:LM.getMessage("verificationSuccessTitle"),
                        text:LM.getMessage("verificationSuccessText"),
                        wasShown:false,
                        type:"success"
                    });
                }
                if(self.model.get('verificationResult').get('status')=="invalid"){
                    var html = "<ul>";
                    _.each(self.model.get('verificationResult').get('field-violations'), function (field) {
                        field+="<li>"+field.get('name')+" (min: "+field.get('min-ects')+")</li>";
                    });
                    _.each(self.model.get('verificationResult').get('rule-group-violations'), function (field) {
                        field+="<li>"+field.get('name')+" (min: "+field.get('min-ects')+", max: "+field.get('max-ects')+")</li>";
                    });
                    _.each(self.model.get('verificationResult').get('compulsory-violations'), function (module) {
                        field+="<li>"+module.get('name')+"</li>";
                    });
                    html+="</ul>";
                    notification = new edu.kit.informatik.studyplan.client.model.system.Notification({
                        title:LM.getMessage("verificationFailTitle"),
                        text:LM.getMessage("verificationFailText")+html,
                        wasShown:false,
                        type:"error"
                    });
                }
                if(notification!==null){
                    edu.kit.informatik.studyplan.client.model.system.NotificationCollection
                        .getInstance().add(notification);
                }
            }
        });
    },
    /**
    *
    */
    rename: function () {
        "use strict";
        console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar] rename")
        this.plan.set('name',this.$el.find("#curPlanName").val());
        this.plan.save({}, {
            success: function () {
                edu.kit.informatik.studyplan.client.model.system.NotificationCollection
                        .getInstance().add(
                    new edu.kit.informatik.studyplan.client.model.system.Notification({
                        title:LM.getMessage("changeSavedTitle"),
                        text:LM.getMessage("changeSavedText"),
                        wasShown:false,
                        type:"success"
                    })
                );
            }
        })
    }
});