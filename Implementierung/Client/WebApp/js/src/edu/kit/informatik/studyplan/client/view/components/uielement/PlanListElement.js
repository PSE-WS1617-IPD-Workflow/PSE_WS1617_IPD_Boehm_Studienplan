goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement");
/**
 * @constructor
 * @param {Object} options
 * @extends {Backbone.View}
 * Class which represents an element in a plan list
 */

edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement = Backbone.View.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement.prototype} */ {
    tagName: 'tr',
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/planListEl.html"),
    plan: null,
    events: {
        "click .showPlan": "showPlan",
        "click button.duplicatePlan": "duplicatePlan",
        "click button.deletePlan": "deletePlan",
        "click button.exportPlan": "exportPlan",
    },
    initialize: function (options) {
        this.plan = options.plan;
    },
    render: function () {
        this.$el.html(this.template({
            plan: this.plan
        }));
        this.delegateEvents();
    },
    /**
     * Method which shows the plan
     */
    showPlan: function (event) {
        "use strict";
        event.preventDefault();
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement] showPlan");
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("plans/" + this.plan.get('id'), {
            trigger: true
        });
    },
    /**
     * Method which exports the plan
     */
    exportPlan: function () {
        "use strict";
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement] exportPlan");
        window.location.href = API_DOMAIN + "/plans/" + this.plan.get('id') + "/pdf?access-token=" + edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('access_token');
    },
    /**
     * Method which duplicates the plan
     */
    duplicatePlan: function () {
        "use strict";
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement] duplicatePlan");
        var planName = prompt(edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("planNameQuestion"), "");
        if (planName === null) {
            return;
        }
        var self = this;
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
        this.plan.fetch({
            // TODO: zurück stellen, wenn nicht erfolgreich
            success: function () {
                var attributes = this.plan.toJSON({
                    method: "PUT"
                });
                delete attributes["plan"]["id"];
                attributes["plan"]["name"] = planName;
                //console.info("[edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement] plan to be duplicated");
                //console.info(this.plan);
                var newPlan = new edu.kit.informatik.studyplan.client.model.plans.Plan(attributes, {
                    parse: true
                });
                //console.info("[edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement] plan duplicate");
                //console.log(attributes);
                //console.info(newPlan);
                // Add to collection
                self.plan.collection.add(newPlan);
                // Send POST request
                newPlan.save(null, {
                    success: function () {
                        // Send PUT request
                        newPlan.set(newPlan.parse(attributes,{}));
                        newPlan.save(null, {
                            success: function () {
                                edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                                edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance().add(
                                    new edu.kit.informatik.studyplan.client.model.system.Notification({
                                        title: LM.getMessage("planDuplicatedTitle"),
                                        text: LM.getMessage("planDuplicatedText"),
                                        wasShown: false,
                                        type: "success"
                                    })
                                );
                            }.bind(this),
                            patch: false
                        });
                    }.bind(this)
                });
            }.bind(this)
        });
    },
    /**
     * Method which deletes the plan
     * @param {boolean=} deleteAll
                            attribute set if all plans will be deleted
     */
    deletePlan: function (deleteAll) {
        "use strict";
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.PlanListElement] deletePlan");
        var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();      
        if ((deleteAll === true) ? true : confirm(LM.getMessage("deletePlanPrompt"))) {
            edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
            this.plan.destroy({
                success: function () {
                    edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
                    edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance().add(
                        new edu.kit.informatik.studyplan.client.model.system.Notification({
                            title: LM.getMessage("planDeletedTitle"),
                            text: LM.getMessage("planDeletedText"),
                            wasShown: false,
                            type: "success"
                        })
                    );
                }
            });
        }
    },
    /**
     * Set's the elements checkbox
     */
    setChecked: function (isChecked) {
        this.$el.find("input[type=checkbox]").prop('checked', isChecked);
    },
    /**
     * Returns wheter or not the checkbox is checked
     */
    isChecked: function () {
        return this.$el.find("input[type=checkbox]").prop('checked');
    }
});
