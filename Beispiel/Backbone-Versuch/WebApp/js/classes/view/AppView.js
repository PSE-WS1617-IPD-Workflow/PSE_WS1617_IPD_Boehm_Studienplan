/**
 * Main View of App
 * @constructor 
 * @extends {Backbone.View}
 */
var AppView = Backbone.View.extend({
    el: "body",
    template: AppTemplateRegistry.getTemplate("templates/wrapper.html"),
    /**
     * @this {AppView}
     */
    initialize : function () {
        "use strict";
        this.render();
    },
    /**
     * @this {AppView}
     */
    render : function () {
        "use strict";
        this.$el.html(this.template());
        this.$el.show();
    }
});