goog.provide("StudyplanApp.Views.SubViews.Login");
/**
 * View showing the login screen
 * @constructor
 * @extends {Backbone.View}
 */
StudyplanApp.Views.SubViews.Login = Backbone.View.extend(/** @lends {StudyplanApp.Views.SubViews.Login.prototype}*/{
    initialize : function () {
        "use strict";
    },
    //tagName : "div",
    template : StudyplanApp.Templating.TemplateRegistry.getInstance().getTemplate("templates/login.html"),
    render : function () {
        "use strict";
        this.$el.html(this.template({}));
        this.$el.show();
    }
});