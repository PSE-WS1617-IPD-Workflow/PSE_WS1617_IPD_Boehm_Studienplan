goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.Semester");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.Semester = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.Semester.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/semester.html"),
    tagName: "tr",
    model: null,
    moduleElements: [],
    initialize: function (options) {
        this.model = options.semester;
        this.reload();
        this.listenTo(this.model, "change", this.reload);
        this.listenTo(this.model, "all", this.reload);
        this.listenTo(this.model, "reset", this.reload);
        this.listenTo(this.model, "add", this.reload);
        this.reload();
    },
    reload: function () {
        this.moduleElements=[];
        this.model.each(function (el) {
            this.moduleElements.push(
                new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox({
                    module: el
                })
            );
        }.bind(this));
        this.render();
    },
    render: function () {
        this.$el.html(this.template({
            semester: this.model
        }));
        this.$el.droppable({
            drop: this.onDrop.bind(this)
        });
        console.info(this.moduleElements);
        _.each(this.moduleElements, function (element) {
            element.render();
            this.$el.find(".semesterModules").append(element.$el);
        }.bind(this));
    },
/**
*
*/
    removeSemester:
        function () {
            "use strict";
        },
    /**
    *@param{Event} event
    *@param{Object} ui
    */
    onDrop:function (event, ui) {
        console.info("[edu.kit.informatik.studyplan.client.view.components.uielement.Semester] drop event");
        var model = ui.helper.data("modelObject");
        if (model.collection!==this.model) {
            model.collection.remove(model);
            model.set('semester', this.model.semesterNum);
            this.model.add(model);
            model.collection = this.model;
            model.save();
        }
    },
    /**
    *
    */
    scrollLeft:
        function () {
            "use strict";
        },
    /**
    *
    */
    scrollRight:
        function () {
            "use strict";
        }
});