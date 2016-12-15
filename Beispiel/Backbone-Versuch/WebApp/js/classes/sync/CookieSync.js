goog.provide("StudyplanApp.Sync.CookieSync");
/**
 * @type function(string, Backbone.Model, Object<string,Object>) : jQuery.Promise
 */
StudyplanApp.Sync.CookieSync = function(method, model, options){
    "use strict";
    var cookieName = model.url();
    options.cookieName = cookieName;
    var handlers = {
        "create" : function (model, options) {
            Cookies.set(options.cookieName,model);
        },
        "read" : function (model, options) {
            return Cookies.getJSON(options.cookieName);
        },
        "update" : function (model, options) {
            if(options.patch){
                var oldModel = Cookies.getJSON(options.cookieName);
                _.map(model.toJSON(),function(value, key){
                    oldModel[key] = value;
                });
                handlers["create"](oldModel, options);
            } else {
                handlers["create"](model, options);
            }
        },
        "delete" : function (model, options) {
            Cookies.remove(options.cookieName);
        }
    };
    return handlers[method](model,options);
}