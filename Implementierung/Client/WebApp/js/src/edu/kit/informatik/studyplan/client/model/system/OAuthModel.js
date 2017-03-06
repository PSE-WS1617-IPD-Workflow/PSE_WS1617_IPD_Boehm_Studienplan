goog.provide("edu.kit.informatik.studyplan.client.model.system.OAuthModel");
/**
 * test
 * @constructor
 * @extends {Backbone.Model}
 * Represents a model which is being saved on the server via OAuth
 */

edu.kit.informatik.studyplan.client.model.system.OAuthModel = Backbone.Model.extend( /** @lends {edu.kit.informatik.studyplan.client.model.system.OAuthModel.prototype}*/ {
    sync: edu.kit.informatik.studyplan.client.storage.OAuthSync,
    save: function (key, val, options) {
        // Handle both `"key", value` and `{key: value}` -style arguments.
        var attrs;
        if (key == null || typeof key === 'object') {
            attrs = key;
            options = val;
        } else {
            (attrs = {})[key] = val;
        }
        options = options || {};
        // Make sure updating awaits a positive server esponse
        _.defaults(options, {
            wait: true
        })
        Backbone.Model.prototype.save.apply(this, [attrs, options])
    },
    destroy: function (options) {
        options = options || {};
        // Make sure updating awaits a positive server response
        options["wait"] = true;
        // Make sure JQuery does not expect any JSON response from server
        options["dataType"] = "text";
        Backbone.Model.prototype.destroy.apply(this, [options])
    }
});