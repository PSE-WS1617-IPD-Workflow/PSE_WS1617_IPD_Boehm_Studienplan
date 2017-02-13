goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.NotificationCentre");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.NotificationCentre = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.NotificationCentre.prototype} */{
    notificationCollection: null,
    tagName: "div",
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/notificationCentre.html"),
    initialize: function (options){
        this.notificationCollection =  edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance();
        this.listenTo(this.notificationCollection, 'add', this.render);
        this.listenTo(this.notificationCollection, 'reset', this.render);
        this.listenTo(this.notificationCollection, 'all', this.render);
    },
    render: function () {
        this.$el.html(this.template());
        var ul = this.$el.find("#notificationCentre");
        this.notificationCollection.each(function (el) {
            if(!el.get('wasShown')) {
                var notification = new edu.kit.informatik.studyplan.client.view.components.uielement.NotificationBox({
                    notification: el
                });
                notification.render();
                ul.append(notification.el);
            }
        });
        this.delegateEvents();
    }
});
