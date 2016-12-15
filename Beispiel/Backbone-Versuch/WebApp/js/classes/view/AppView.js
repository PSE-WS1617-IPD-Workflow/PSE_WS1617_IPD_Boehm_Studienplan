goog.provide("StudyplanApp.Views.AppView");
/**
 * Main View of App
 * @constructor 
 * @extends {Backbone.View}
 */
StudyplanApp.Views.AppView = Backbone.View.extend({
    el: "body",
    template: StudyplanApp.Templating.TemplateRegistry.getInstance().getTemplate("templates/wrapper.html"),
    /**
     * @this {StudyplanApp.Views.AppView}
     */
    initialize : function () {
        "use strict";
        this.render();
    },
    /**
     * @this {StudyplanApp.Views.AppView}
     */
    render : function () {
        "use strict";
        this.$el.html(this.template());
        this.$el.show();
    }
});