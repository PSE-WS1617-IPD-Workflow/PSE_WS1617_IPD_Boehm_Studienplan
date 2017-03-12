goog.provide("edu.kit.informatik.studyplan.client.model.system.NotificationCollection");
/**
 * @class
 * @name edu.kit.informatik.studyplan.client.model.system.NotificationCollection
 * @extends {Backbone.Collection}
 * Singleton which contains a collection of notifications
 */

edu.kit.informatik.studyplan.client.model.system.NotificationCollection = (function () {
    /**
     * @this {edu.kit.informatik.studyplan.client.model.system.NotificationCollection}
     */
    "use strict";
    /**
     * @type {edu.kit.informatik.studyplan.client.model.system.NotificationCollection}
     */
    var instance = null;
    /**
     * The actual NotificationCollection class
     * @constructor
     * @name edu.kit.informatik.studyplan.client.model.system.NotificationCollection
     */
    var Constructor = Backbone.Collection.extend({
        /**
         * Timeout of how long one may not show the same error message again after it has been shown once (in ms)
         */
        justSentTime: 2000,
        model: edu.kit.informatik.studyplan.client.model.system.Notification,
        justSentNotifications: [],
        
        add: function (models) {
            var singular = !_.isArray(models);
            models = singular ? [models] : models.slice();
            var notifications = [];
            for (var i = 0; i < models.length; i++) {
                var notificationKey = models[i].get('title') + "\u001F" + models[i].get('text');
                if(!this.justSentNotifications[notificationKey]){
                    this.justSentNotifications[notificationKey] = true;
                    window.setTimeout(this.removeJustSent.bind(this,notificationKey), this.justSentTime);
                    notifications.push(models[i]);
                }
            }
            Backbone.Collection.prototype.add.call(this, notifications)
        },
        removeJustSent: function (key) {
            console.log("resetting "+key)
            this.justSentNotifications[key]=false;
        }
    });

    return {
        /**
         * @return {edu.kit.informatik.studyplan.client.model.system.NotificationCollection}
         */
        getInstance: function () {
            if (instance === null) {
                instance = new Constructor();
            }
            return instance;
        }
    };
}());