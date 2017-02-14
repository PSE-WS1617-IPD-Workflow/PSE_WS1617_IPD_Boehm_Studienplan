goog.provide("edu.kit.informatik.studyplan.client.model.system.Notification");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Model}
 * Object which represents a notification
 * Contained Information:
 * - string title: Title of the notification
 * - string text: Text of the notification
 * - boolean wasShown: Whether the notification has been shown yet
 * - string type: Type of notification (at the moment only "success", "error" or none)
 */

edu.kit.informatik.studyplan.client.model.system.Notification = Backbone.Model.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.Notification.prototype}*/{
    
});