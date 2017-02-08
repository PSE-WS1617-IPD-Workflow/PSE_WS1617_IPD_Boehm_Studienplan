goog.provide("edu.kit.informatik.studyplan.client.model.system.OAuthModel");
/**
 * test
 * @constructor
 * @extends {Backbone.Model}
 */

edu.kit.informatik.studyplan.client.model.system.OAuthModel = Backbone.Model.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.OAuthModel.prototype}*/{
    sync: edu.kit.informatik.studyplan.client.storage.OAuthSync,
    save: function (options) {
        options = options || {};
        options["wait"]=true;
        Backbone.Model.prototype.save.apply(this,[options])
    },
    destroy: function (options) {
        options = options || {};
        options["wait"]=true;
        Backbone.Model.prototype.destroy.apply(this,[options])
    }
});