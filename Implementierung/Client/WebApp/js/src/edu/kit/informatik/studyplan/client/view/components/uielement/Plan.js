goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.Plan");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.Plan = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.Plan.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/plan.html"),
    model: null,
    semesterElements : [],
    isPreferencable: true,
    isPassedPlan: false,
    initialize: function (options) {
        this.model = options.plan;
        this.isPreferencable = (typeof options.isPreferencable !== "undefined") ? options.isPreferencable : this.isPreferencable;
        this.isPassedPlan = (typeof options.isPassedPlan !== "undefined") ? options.isPassedPlan : this.isPassedPlan;
        this.listenTo(this.model, "change", this.reload);
        
        this.reload();
    },
    reload: function () {
        var semesterCol = this.model.get('semesterCollection');
        this.semesterElements = [];
        semesterCol.each((function (semester) {
            this.semesterElements.push(
                new edu.kit.informatik.studyplan.client.view.components.uielement.Semester({
                    semester: semester,
                    isRemovable: true,
                    isPreferencable: this.isPreferencable,
                    isPassedPlan: this.isPassedPlan
                })
            );
        }).bind(this));
        this.render();
    },
    render: function () {
        this.$el.html(this.template({
            plan: this.model
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
                    
    },
    /**
    *
    */
    addSemester: function () {
            "use strict";
    },
    /**
    *
    */
    onChange: function () {
        "use strict";
    }
});