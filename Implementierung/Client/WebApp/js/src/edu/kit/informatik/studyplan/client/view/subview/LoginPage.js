goog.provide("edu.kit.informatik.studyplan.client.view.subview.LoginPage");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.LoginPage = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.LoginPage.prototype} */{
    tagName: "div",
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate('resources/templates/subview/loginPage.html'),
    render: function () {
        this.$el.html("");
        this.$el.html(this.template({}));
    }
});