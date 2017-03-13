goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.Plan");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 * Class which represents a plan in the view
 */

edu.kit.informatik.studyplan.client.view.components.uielement.Plan = Backbone.View.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.Plan.prototype} */ {
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/plan.html"),
    model: null,
    events: {
        "click .addSemesterButton": "addSemester"
    },
    semesterElements: [],
    /** 
     * Whether semesters are addable to the plan
     */
    isAddable: true,
    /**
     * Whether modules are preferencable in the plan
     */
    isPreferencable: true,
    /**
     * Wether modules are draggable
     */
    isDraggable: true,
    /**
     * Whether the plan is a plan of (only) passed modules
     */
    isPassedPlan: false,
    initialize: function (options) {
        this.isAddable = (typeof options.isAddable !== "undefined") ? options.isAddable : this.isAddable;
        this.isDraggable = (typeof options.isDraggable !== "undefined") ? options.isDraggable : this.isDraggable;
        this.model = options.plan;
        this.isPreferencable = (typeof options.isPreferencable !== "undefined") ? options.isPreferencable : this.isPreferencable;
        this.isPassedPlan = (typeof options.isPassedPlan !== "undefined") ? options.isPassedPlan : this.isPassedPlan;
        this.listenTo(this.model, "change", this.reload.bind(this));

        this.reload();
    },
    /**
     * Method which reloads and renders the internal ui element view collections once the model changes
     */
    reload: function () {
        //console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.Plan] plan is being reloaded, plan:")
        //console.log(this.model);
        var semesterCol = this.model.get('semesterCollection');
        this.semesterElements = [];
        semesterCol.each((function (semester) {
            this.semesterElements.push(
                new edu.kit.informatik.studyplan.client.view.components.uielement.Semester({
                    semester: semester,
                    isRemovable: true,
                    isPreferencable: this.isPreferencable,
                    isDraggable: this.isDraggable,
                    isPassedPlan: this.isPassedPlan
                })
            );
        }).bind(this));
        this.render();
    },
    render: function () {
        this.$el.html(this.template({
            plan: this.model,
            isAddable: this.isAddable
        }));
        _.each(this.semesterElements, (function (el) {
            el.render();
            this.$el.find(".semesters").append(el.$el);
        }).bind(this));
        _.each(this.model.get('verificationResult').get('violations'), function (violation) {
            _.each(this.semesterElements, (function (semester) {
                semester.setRedBorder(violation.get('first').get('id'));
                semester.setRedBorder(violation.get('second').get('id'));
            }).bind(this));
        }.bind(this));
        this.delegateEvents();
    },
    /**
     * Method which adds a semester to the plan
     */
    addSemester: function () {
        "use strict";
        if(this.model.get('semesterCollection').length>200) {
            alert(edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("semesterLimit"));
            return;
        }
        var newSemester = new edu.kit.informatik.studyplan.client.model.plans.Semester({
            planId: this.model.get("id"),
            semesterNum: this.model.get('semesterCollection').length,
            modules: []
        }, {
            parse: true,
            collection: this.model.get('semesterCollection')
        });

        this.model.get('semesterCollection').push(newSemester);
        this.reload();
    }
});