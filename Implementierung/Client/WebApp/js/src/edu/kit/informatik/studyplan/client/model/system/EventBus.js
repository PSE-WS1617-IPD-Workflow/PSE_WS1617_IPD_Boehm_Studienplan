goog.provide("edu.kit.informatik.studyplan.client.model.system.EventBus");
/**
 * @extends {Backbone.Event}
 * This class represents an event bus which is used to communicate between ModuleBoxes (which are being clicked on) and view elements which might (in response to this) show further information on the module.
 * Used events:
 *  - "showModuleInfo"
 *  - "hideModuleInfo"
 */
edu.kit.informatik.studyplan.client.model.system.EventBus = {};
/**
 * @param {string} key
 * @param {...*} options
 */
edu.kit.informatik.studyplan.client.model.system.EventBus.trigger = function (key, options) {};
/** 
 * @param {string} key
 * @param {function(this:*,...*):*} callback
 */
edu.kit.informatik.studyplan.client.model.system.EventBus.on = function (key, callback) {};

_.extend(edu.kit.informatik.studyplan.client.model.system.EventBus, Backbone.Events);