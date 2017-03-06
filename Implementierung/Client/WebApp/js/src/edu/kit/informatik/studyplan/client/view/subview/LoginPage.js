goog.provide("edu.kit.informatik.studyplan.client.view.subview.LoginPage");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.LoginPage = Backbone.View.extend( /** @lends {edu.kit.informatik.studyplan.client.view.subview.LoginPage.prototype} */ {
    /**
     * the tag name of the described html element in the template
     */
    tagName: "div",
    /**
     * html template for this element
     */
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate('resources/templates/subview/loginPage.html'),
    /*
     * renders this page
     */
    render: function () {
        this.$el.html("");
        this.$el.html(this.template({
            sessionInformation: edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance()
        }));
    }
});