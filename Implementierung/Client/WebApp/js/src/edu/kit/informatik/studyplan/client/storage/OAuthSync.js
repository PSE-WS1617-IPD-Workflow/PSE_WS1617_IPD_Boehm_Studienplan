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
        "crossDomain" : true,
    });
    var errorCallback = options["error"];
    options["error"] = function (xhr, textStatus, errorThrown) {
        console.info("[edu.kit.informatik.studyplan.client.storage.OAuthSync] Method: "+method);
        console.info(model);
        edu.kit.informatik.studyplan.client.storage.OAuthSync.errorCallback(xhr, textStatus, errorThrown);
        if(errorCallback) errorCallback.call(options.context, xhr, textStatus, errorThrown)
    }
    return Backbone.sync(method, model, options);
};
edu.kit.informatik.studyplan.client.storage.OAuthSync.errorCallback = function (xhr, textStatus, errorThrown) {
    console.group()
    console.info("[edu.kit.informatik.studyplan.client.storage.OAuthSync] Request failed: "+textStatus+" ("+errorThrown+")");
    console.log("[edu.kit.informatik.studyplan.client.storage.OAuthSync] Status code:"+xhr.status);
    console.info(xhr);
    console.groupEnd();
    var notificationCollection = edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance();
    // Retrieve error message
    var msg = "";
    if(textStatus=="timeout"){
        msg = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("connectionErrorText-timeout")
    }
    if(textStatus=="abort"){
        msg = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("connectionErrorText-abort")
    }
    if(textStatus=="parsererror"){
        msg = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("connectionErrorText-500")
    }
    if(textStatus=="error"){
        msg = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("connectionErrorText-" + xhr.status)
        if (xhr.status===0){
            msg = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("connectionErrorText-timeout");
        }
    }
    var notification = new edu.kit.informatik.studyplan.client.model.system.Notification({
        title: edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("connectionErrorTitle"),
        text: msg,
        wasShown: false,
        type: "error"
    });
    notificationCollection.add(notification);
    edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
// Logout when unauthorized
    if (xhr.status===401) {
        edu.kit.informatik.studyplan.client.model.user.SessionInformation
            .getInstance()
            .set('access_token', undefined);
        edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().save();
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/login", {trigger: true});
        edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
            .add(
                new edu.kit.informatik.studyplan.client.model.system.Notification({
                        title: LM.getMessage("realAuthEndTitle"),
                        text: LM.getMessage("realAuthEndText"),
                        wasShown: false,
                        type: "error"
                })
            );
    }
}