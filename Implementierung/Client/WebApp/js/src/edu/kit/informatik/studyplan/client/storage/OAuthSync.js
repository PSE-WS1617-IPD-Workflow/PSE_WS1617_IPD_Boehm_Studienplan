goog.provide("edu.kit.informatik.studyplan.client.storage.OAuthSync");
var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
/**
 * @param {string} method The action to take: "create", "read", "update", "patch" or "delete"
 * @param {Object} model The model to transfer to the server
 * @param {Object<string,Object>} options The options for the transfer
 */
edu.kit.informatik.studyplan.client.storage.OAuthSync = function (method, model, options) {
    "use strict";
    // Util
    /**
     * @param {string} message The message to be used in the error
     */
    function throwError(message) {
        throw new Error(message);
    }
    var httpVerbMap = {
        "create"    :   "POST",
        "read"      :   "GET",
        "update"    :   "PUT",
        "patch"     :   "PATCH",
        "delete"    :   "DELETE"
    };
    // Sync logic
    var params = {};
    if (typeof httpVerbMap[method] !== undefined) {
        params = {type: httpVerbMap[method], dataType: 'json'};
    } else {
        throwError(LM.getMessage("error.invalidMethod"));
    }
    if (typeof options.url !== "undefined") {
        params.url = _.result(model, 'url') || throwError(LM.getMessage("error.noUrl"));
    }
    if (typeof model !== "undefined") {
        params.contentType = 'application/json';
        params.data = JSON.stringify(options.attrs || model.toJSON(options));
    }
    var error = options.error;
    options.error = function (xhr, textStatus, errorThrown) {
        options.textStatus = textStatus;
        options.errorThrown = errorThrown;
        if (typeof error !== "undefined") {
            error.call(options.context, xhr, textStatus, errorThrown);
        }
    };
    var accessToken = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get("access_token");
    params.header = {
        "Authorization" :   "Bearer "+accessToken;
    };
    var xhr = options.xhr = Backbone.ajax(_.extend(params, options));
    model.trigger('request', model, xhr, options);
    return xhr;
};