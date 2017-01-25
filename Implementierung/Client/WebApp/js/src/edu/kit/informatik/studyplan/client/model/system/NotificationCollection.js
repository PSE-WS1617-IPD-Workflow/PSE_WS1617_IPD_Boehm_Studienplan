goog.provide("edu.kit.informatik.studyplan.client.model.system.NotificationCollection");
/**
 * @class
 * @name edu.kit.informatik.studyplan.client.model.system.NotificationCollection
 * @extends {Backbone.Collection}
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
     * @constructor
     * @name edu.kit.informatik.studyplan.client.model.system.NotificationCollection
     */
    var Constructor = Backbone.Collection.extend({
        model : edu.kit.informatik.studyplan.client.model.system.Notification
    });
    
    return {
        /**
        * @return {edu.kit.informatik.studyplan.client.model.system.NotificationCollection}
        */
        getInstance : function () {
            if (instance === null) {
                instance = new Constructor();
            }
            return instance;
        }
    };
}());