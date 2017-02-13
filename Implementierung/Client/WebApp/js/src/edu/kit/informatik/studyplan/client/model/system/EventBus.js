goog.provide("edu.kit.informatik.studyplan.client.model.system.EventBus");
/**
 * @extends {Backbone.Event}
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