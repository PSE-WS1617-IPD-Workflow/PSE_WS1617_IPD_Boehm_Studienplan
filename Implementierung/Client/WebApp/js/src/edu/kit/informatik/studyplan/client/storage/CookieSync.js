goog.provide("edu.kit.informatik.studyplan.client.storage.CookieSync");
/**
 * @param {string=} method The action to take: "create", "read", "update", "patch" or "delete"
 * @param {Backbone.Model=} model The model to transfer to the server
 * @param {Object<string,Object>=} options The options for the transfer
 */
edu.kit.informatik.studyplan.client.storage.CookieSync = function (method, model, options) {
    "use strict";
    var create = function (model) {
            Cookies.set(_.result(model, 'url'), model.toJSON());
            return model.toJSON();
        };
    var methods = {
        "create" : create,
        "update" : create,
        "delete" : function (model) {
            Cookies.remove(_.result(model, 'url'));
        },
        "read" : function (model) {
            return Cookies.getJSON(_.result(model, 'url'));
        }
    };
    if (typeof methods[method] !== "undefined") {
        var result = methods[method](model);
        if(result===undefined&&options&&options.error){
            options.error(null, "[edu.kit.informatik.studyplan.client.storage.CookieSync] Invalid method " + method, 400);
        }
        if(options&&options.success){
            options.success(result);
        }
        return result;
    } else {
        if(options&&options.error){
            options.error(null, "[edu.kit.informatik.studyplan.client.storage.CookieSync] Invalid method " + method, 400);
        }
        throw new Error("[edu.kit.informatik.studyplan.client.storage.CookieSync] Invalid method " + method);
    }
};