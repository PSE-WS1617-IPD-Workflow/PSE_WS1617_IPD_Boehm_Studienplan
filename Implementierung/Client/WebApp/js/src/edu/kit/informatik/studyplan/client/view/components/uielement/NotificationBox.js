goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.NotificationBox");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.NotificationBox = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.NotificationBox.prototype} */{
    model: null,
    tagName: "li",
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/notificationBox.html"),
    events: {
        "click .close" : "onClose"
    },
    initialize: function (options){
        this.model = options.notification;
    },
    render: function () {
        this.$el.html(this.template({notification: this.model}));
        this.$el.addClass(this.model.get('type'))
        var self = this;
        window.setTimeout(function () {
            self.onClose();
        }, 2000);
        this.delegateEvents();
    },
    /**
    *
    */
    onClose: function () {
        "use strict";
        this.model.set('wasShown', true);
        this.remove();
    }
});