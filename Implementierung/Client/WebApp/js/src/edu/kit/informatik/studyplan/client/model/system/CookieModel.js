goog.provide("edu.kit.informatik.studyplan.client.model.system.CookieModel");
/**
 * @constructor
 * @extends {Backbone.Model}
 * Represents a model which is being saved in a cookie
 */

edu.kit.informatik.studyplan.client.model.system.CookieModel = Backbone.Model.extend( /** @lends {edu.kit.informatik.studyplan.client.model.system.CookieModel.prototype}*/ {
    sync: edu.kit.informatik.studyplan.client.storage.CookieSync
});