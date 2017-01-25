goog.provide("edu.kit.informatik.studyplan.client.storage.OAuthSync");
/**
 * @param {string} method The action to take: "create", "read", "update", "patch" or "delete"
 * @param {Backbone.Model|null|undefined} model The model to transfer to the server
 * @param {Object<string,Object>} options The options for the transfer
 */
edu.kit.informatik.studyplan.client.storage.OAuthSync = function (method, model, options) {
    "use strict";
    var sendAuthentication = function (xhr) {
        xhr.setRequestHeader("Authorization", "Bearer " + edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('access_token'));
    }
    options = _.extend(options, {
        beforeSend: sendAuthentication,
        "crossDomain" : true
    });
    return Backbone.sync(method, model, options);
};