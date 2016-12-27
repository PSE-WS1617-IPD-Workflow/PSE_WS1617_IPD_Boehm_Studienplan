goog.provide("StudyplanApp.Views.SubViews.Header");
/**
 * View showing the header of the App
 * @constructor
 * @extends {Backbone.View}
 */
StudyplanApp.Views.SubViews.Header = Backbone.View.extend(/** @lends {StudyplanApp.Views.SubViews.Header.prototype}*/{
    initialize: function(){ 
        
    },
    tagName : "div",
    template : StudyplanApp.Templating.TemplateRegistry.getInstance().getTemplate("templates/header.html"),
    render : function () {
        "use strict";
        this.$el.html(this.template({}));
        this.$el.show();
    }
});