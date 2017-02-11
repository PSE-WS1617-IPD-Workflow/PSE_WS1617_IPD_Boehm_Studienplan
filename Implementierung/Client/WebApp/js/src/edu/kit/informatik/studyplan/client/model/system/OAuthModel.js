goog.provide("edu.kit.informatik.studyplan.client.model.system.OAuthModel");
/**
 * test
 * @constructor
 * @extends {Backbone.Model}
 */

edu.kit.informatik.studyplan.client.model.system.OAuthModel = Backbone.Model.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.OAuthModel.prototype}*/{
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
        _.defaults(options,{
            wait: true
        })
        Backbone.Model.prototype.save.apply(this,[attrs, options])
    },
    destroy: function (options) {
        options = options || {};
        options["wait"]=true;
        Backbone.Model.prototype.destroy.apply(this,[options])
    }
});