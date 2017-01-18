goog.provide("edu.kit.informatik.studyplan.client.model.system.EventBus");
/**
 * @constructor
 */

edu.kit.informatik.studyplan.client.model.system.EventBus = (function () {
    "use strict";
    /**
     * @type {Backbone.Events}
     */
    var instance = {};
    _.extend(instance, Backbone.Events);
    return {
        /**
        * @return {Backbone.Events}
        */
        getInstance : function () {
            return instance;
        }
    };
}());