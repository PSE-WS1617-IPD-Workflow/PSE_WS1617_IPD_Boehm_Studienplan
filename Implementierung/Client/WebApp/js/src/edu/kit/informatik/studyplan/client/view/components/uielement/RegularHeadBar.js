goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar}
 * Class which represents the head bar in the plan view
 */

edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar = edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar.prototype}*/ {
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/regularHeadBar.html"),
    model: null,
    dialog: null,
    events: {
        "change #curPlanName": "rename",
        "click #generatePlan": "generate",
        "click #verifyPlan": "verify",
        "click button.mainPageNavigation": "goHome"
    },
    initialize: function (options) {
        "use strict";
        this.model = options.plan;
        this.listenTo(this.model, "change", this.render.bind(this));
    },
    render: function () {
        "use strict";
        this.$el.html(this.template({
            plan: this.model
        }));
        this.delegateEvents();
    },
    /**
     * Method which initiates plan generation wizard
     */
    generate: function () {
        "use strict";
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar] generate");
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("plans/" + this.model.get('id') + "/generate", {
            trigger: true
        });
    },
    goHome: function () {
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/", {
            trigger: true
        })
    },
    /**
     * Method which initiates plan verification
     * The following line is necessary due to the jquery ui dialog opened in this function
     * @suppress {missingProperties}
     */
    verify: function () {
        "use strict";
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar] verify");
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
        this.model.get('verificationResult').fetch({
            success: function () {
                edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                var notification = null;
                if (this.model.get('verificationResult').get('status') == "valid") {
                    notification = new edu.kit.informatik.studyplan.client.model.system.Notification({
                        title: LM.getMessage("verificationSuccessTitle"),
                        text: LM.getMessage("verificationSuccessText"),
                        wasShown: false,
                        type: "success"
                    });
                }
                if (this.model.get('verificationResult').get('status') == "invalid") {
                    // Turned to true, if the dialog needs to be shown
                    var showDialog = (this.model.get('verificationResult').get('field-violations').length>0||
                                       this.model.get('verificationResult').get('rule-group-violations').length > 0 ||
                                       this.model.get('verificationResult').get('compulsory-violations').length > 0);
                    var html = edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate(
                        "resources/templates/components/uielement/verificationDialog.html"
                    )({
                        verification: this.model.get('verificationResult')
                    });
                    if(showDialog) {
                        if(this.dialog!=null){
                            $(this.dialog).dialog("close")
                            this.dialog=null;
                        }
                        var options = {
                            title: LM.getMessage("verificationFailTitle"),
                            resizable: false,
                            height: "auto",
                            minWidth: 400,
                            minHeight: 300,
                            modal: false,
                            draggable: true,
                            buttons: {},
                        }
                        options["buttons"][LM.getMessage('OK')] = function () {
                            $(this.dialog).dialog("close");
                            this.dialog=null;
                        }.bind(this);
                        this.dialog = $(html).dialog(options);
                        this.dialog.show();
                    } else {
                        notification = new edu.kit.informatik.studyplan.client.model.system.Notification({
                            title: LM.getMessage("verificationFailTitle"),
                            text: LM.getMessage("verificationFailText"),
                            wasShown: false,
                            type: "error"
                        });
                    }
                }
                if (notification !== null) {
                    edu.kit.informatik.studyplan.client.model.system.NotificationCollection
                        .getInstance().add(notification);
                }
            }.bind(this)
        });
    },
    /**
     * Method which renames the plan
     */
    rename: function () {
        "use strict";
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar] rename")
        var name = this.$el.find("#curPlanName").val();
        if (name.length > 100) {
            edu.kit.informatik.studyplan.client.model.system.NotificationCollection
                .getInstance().add(
                    new edu.kit.informatik.studyplan.client.model.system.Notification({
                        title: LM.getMessage("nameTooLongTitle"),
                        text: LM.getMessage("nameTooLongText"),
                        wasShown: false,
                        type: "success"
                    })
                );
        }
        this.model.set('name', name);
        this.model.save(null, {
            success: function () {
                edu.kit.informatik.studyplan.client.model.system.NotificationCollection
                    .getInstance().add(
                        new edu.kit.informatik.studyplan.client.model.system.Notification({
                            title: LM.getMessage("changeSavedTitle"),
                            text: LM.getMessage("changeSavedText"),
                            wasShown: false,
                            type: "success"
                        })
                    );
            }
        })
    }
});