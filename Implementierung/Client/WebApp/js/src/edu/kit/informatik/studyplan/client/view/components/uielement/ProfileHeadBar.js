goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ProfileHeadBar");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar}
 * Class which represents the head bar shown in the profile view
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ProfileHeadBar = edu.kit.informatik.studyplan.client.view.components.uielement.PlanHeadBar.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.RegularHeadBar.prototype}*/ {
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/profileHeadBar.html"),
    model: null,
    events: {
        "click #deleteUser": "deleteUser",
        "click #save": "savePlan",
        "click button.mainPageNavigation": "goHome"
    },
    initialize: function (options) {
        "use strict";
        this.model = options.plan;
        this.listenTo(this.model, "change", this.render);
    },
    render: function () {
        "use strict";
        this.$el.html(this.template({
            plan: this.model
        }));
        this.delegateEvents();
    },
    /**
     * Saves the passed modules
     */
    savePlan: function (pushToServer) {
        "use strict";
        pushToServer = (typeof pushToServer !== "undefined") ? pushToServer : true;
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.ProfileHeadBar] save");
        var planObject = this.model.toJSON({
            method: "put",
            planModule: true,
            includePassed: true
        });
        var modules = planObject["plan"]["modules"];
        var student = edu.kit.informatik.studyplan.client.model.user.SessionInformation
            .getInstance()
            .get('student');
        student.get('passedModules')
            .reset(modules);
        if (pushToServer) {
            student.save(null,{
                success: function () {
                    //console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.ProfileHeadBar] save done");
                    edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                    var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
                    edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
                        .add(
                            new edu.kit.informatik.studyplan.client.model.system.Notification({
                                title: LM.getMessage("profileSavedTitle"),
                                text: LM.getMessage("profileSavedText"),
                                wasShown: false,
                                type: "success"
                            })
                        );
                }
            });
        } else {
            edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
        }

    },
    /**
     * Method which deletes the user
     */
    deleteUser: function () {
        var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
        if (confirm(LM.getMessage("deleteUserPrompt"))) {
            //DELETE USER
            var student = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('student');
            student.destroy({
                success: function () {
                    edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance().add(
                        new edu.kit.informatik.studyplan.client.model.system.Notification({
                            title: LM.getMessage("deleteUser"),
                            text: LM.getMessage("deleteUserSuccess"),
                            wasShown: false,
                            type: "success"
                        }));
                    edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("logout", {
                        trigger: true
                    })

                }
            });

        }
    },
    /**
     * Method which leads the user back to the main page
     */
    goHome: function () {
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/", {
            trigger: true
        })
    },


});