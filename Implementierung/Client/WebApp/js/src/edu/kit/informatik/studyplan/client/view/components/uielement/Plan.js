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
    initialize: function (options) {
        this.model = options.plan;
        var semesterCol = this.model.get('semesterCollection');
        semesterCol.each((function (semester) {
            this.semesterElements.push(
                new edu.kit.informatik.studyplan.client.view.components.uielement.Semester({
                    semester: semester,
                    isRemovable: true,
                    isPreferencable: true
                })
            );
        }).bind(this));
    },
    render: function () {
        this.$el.html(this.template({
            plan: this.model
        }));
        _.each(this.semesterElements, (function (el) {
            el.render();
            this.$el.find(".semesters").append(el.$el);
        }).bind(this));
        
    },
    /**
    *
    */
    addSemester:
        function () {
            "use strict";
        },
    /**
    *
    */
    onChange:
        function () {
            "use strict";
        }
});