goog.provide("edu.kit.informatik.studyplan.client.view.subview.NotFoundPage");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.NotFoundPage = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.LoginPage.prototype} */{
    tagName: "div",
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate('resources/templates/subview/notFound.html'),
    render: function () {
        this.$el.html("");
        this.$el.html(this.template({}));
    }
});