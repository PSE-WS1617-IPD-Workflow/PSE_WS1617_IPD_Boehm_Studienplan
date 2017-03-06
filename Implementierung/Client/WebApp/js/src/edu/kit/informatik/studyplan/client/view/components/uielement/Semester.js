goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.Semester");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 * Class which represents a semester in a plan
 */

edu.kit.informatik.studyplan.client.view.components.uielement.Semester = Backbone.View.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.Semester.prototype} */ {
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/semester.html"),
    tagName: "tr",
    model: null,
    moduleElements: [],
    /**
     * Whether the semester is passed
     */
    isPassedSemester: false,
    /**
     * Whether the plan is a plan with (only) passed modules
     */
    isPassedPlan: false,
    /**
     * Whether the modules in the plan are preferencable
     */
    isPreferencable: true,
    byId: {},
    events: {

    },
    initialize: function (options) {
        this.model = options.semester;
        this.isRemovable = (typeof options.isRemovable !== "undefined") ? options.isRemovable : this.isRemovable;
        this.isPreferencable = (typeof options.isPreferencable !== "undefined") ? options.isPreferencable : this.isPreferencable;
        this.isPassedSemester = (typeof options.isPassedSemester !== "undefined") ? options.isPassedSemester : this.isPassedSemester;
        this.isPassedPlan = (typeof options.isPassedPlan !== "undefined") ? options.isPassedPlan : this.isPassedPlan;
        this.reload();
        this.listenTo(this.model, "change", this.reload);
        this.listenTo(this.model, "destroy", this.reload);
        this.listenTo(this.model, "all", this.reload);
        this.listenTo(this.model, "reset", this.reload);
        this.listenTo(this.model, "add", this.reload);
        this.reload();
    },
    reload: function () {
        this.moduleElements = [];
        this.model.each(function (el) {
            var removable = true;
            if (!this.isPassedPlan && el.get('passed')) {
                removable = false;
            }
            var draggable = true;
            if (!this.isPassedPlan && el.get('passed')) {
                draggable = false;
            }
            var module = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox({
                module: el,
                isDraggable: draggable,
                isRemovable: removable,
                isPreferencable: this.isPreferencable,
                isPassedPlanModule: this.isPassedPlan
            });
            this.byId[el.get('id')] = module;
            module.setRedBorder(false);
            this.moduleElements.push(module);
        }.bind(this));
        this.render();
    },
    /**
     * @this {Backbone.View}
     * @suppress {missingProperties}
     * @return {Backbone.View|null}
     */
    render: function () {
        this.$el.html(this.template({
            semester: this.model
        }));
        this.$el.droppable({
            drop: this.onDrop.bind(this)
        });
        _.each(this.moduleElements, function (element) {
            element.render();
            this.$el.find(".semesterModules").append(element.$el);
        }.bind(this));
        this.delegateEvents();
        return null;
    },
    setRedBorder: function (id) {
        if (this.byId[id]) {
            this.byId[id].setRedBorder(true);
        }
    },
    /**
     *
     */
    removeSemester: function () {
        "use strict";
    },
    /**
     *@param{Event} event
     *@param{Object} ui
     */
    onDrop: function (event, ui) {
        //console.info("[edu.kit.informatik.studyplan.client.view.components.uielement.Semester] drop event");
        //TODO: Make module deletable when it wasn't before!
        var droppedElement = ui.helper.data("viewObject");
        /**
         * @type {edu.kit.informatik.studyplan.client.model.module.Module}
         */
        var droppedModel = droppedElement.model;
        if (!(droppedModel.collection instanceof edu.kit.informatik.studyplan.client.model.plans.Semester)) {
            droppedModel = /** @type {edu.kit.informatik.studyplan.client.model.module.Module} */ (droppedModel.clone());
            droppedModel.collection = null;
            // Do not insert a module twice
            if (this.model.collection.containsModule(droppedModel.get('id'))) {
                var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
                edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance().add(
                    new edu.kit.informatik.studyplan.client.model.system.Notification({
                        title: LM.getMessage('notInsertTwiceTitle'),
                        text: LM.getMessage('notInsertTwiceText'),
                        wasShown: false,
                        type: "error"
                    })
                );
                return false;
            }
        }
        if (droppedModel.collection !== this.model) {
            var oldCol = droppedModel.collection;
            var oldSem = droppedModel.get('semester');
            if (droppedModel.collection !== null) {
                droppedModel.collection.remove(droppedModel);
            }
            droppedModel.set('semester', this.model.semesterNum);
            if (this.isPassedPlan) {
                droppedModel.set('passed', true);
            }
            this.model.add(droppedModel);
            droppedModel.collection = this.model;
            if (!this.isPassedPlan) {
                droppedModel.save(null, {
                    error: function () {
                        this.model.remove(droppedModel);
                        if (oldCol !== null) {
                            droppedModel.set('semester', oldSem);
                            oldCol.add(droppedModel);
                        }
                    }.bind(this)
                });
            }
        }
    },
    /**
     *
     */
    scrollLeft: function () {
        "use strict";
    },
    /**
     *
     */
    scrollRight: function () {
        "use strict";
    }
});