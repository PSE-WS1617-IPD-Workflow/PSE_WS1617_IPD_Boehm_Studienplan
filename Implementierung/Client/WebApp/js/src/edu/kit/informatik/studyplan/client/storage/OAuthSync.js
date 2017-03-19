goog.provide("edu.kit.informatik.studyplan.client.storage.OAuthSync");
/**
 * @param {string} method The action to take: "create", "read", "update", "patch" or "delete"
 * @param {Backbone.Model|null|undefined} model The model to transfer to the server
 * @param {Object<string,Object>} options The options for the transfer
 * Model which handles the saving/loading of models which were saved on our REST-Webservice via OAuth
 * This method also handles possible errors which might occur during the request by creating a fitting Notification object,
 * of which the corresponding NotificationBox may then be shown by the MainView
 */
edu.kit.informatik.studyplan.client.storage.OAuthSync = function (method, model, options) {
    "use strict";
    var sendAuthentication = function (xhr) {
        xhr.setRequestHeader("Authorization", "Bearer " + edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('access_token'));
    }
    options = _.extend(options, {
        beforeSend: sendAuthentication,
        "crossDomain": true,
    });
    var errorCallback = options["error"];
    options["error"] = function (method, model, xhr, textStatus, errorThrown) {
        //console.info("[edu.kit.informatik.studyplan.client.storage.OAuthSync] Method: " + method);
        //console.info(model);
        edu.kit.informatik.studyplan.client.storage.OAuthSync.errorCallback(method, model, xhr, textStatus, errorThrown);
        if (errorCallback) errorCallback.call(options.context, xhr, textStatus, errorThrown)
    }.bind(this, method, model)
    return Backbone.sync(method, model, options);
};
edu.kit.informatik.studyplan.client.storage.OAuthSync.errorCallback = function (method, model, xhr, textStatus, errorThrown) {
    
    var notificationCollection = edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance();
    var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
    
    // Logout when unauthorized
    if (xhr.status === 401) {
        // If already logged out, ignore
        if(!edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().isLoggedIn()) {
            return;
        }
        edu.kit.informatik.studyplan.client.model.user.SessionInformation
            .getInstance()
            .set('access_token', undefined);
        edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().save();
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/login", {
            trigger: true
        });
        edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
            .add(
                new edu.kit.informatik.studyplan.client.model.system.Notification({
                    title: LM.getMessage("realAuthEndTitle"),
                    text: LM.getMessage("realAuthEndText"),
                    wasShown: false,
                    type: "error"
                })
            );
        return;
    }
    
    // Fetch fitting HTTP Method
    var httpMethod = {
        'create': 'POST',
        'update': 'PUT',
        'patch': 'PATCH',
        'delete': 'DELETE',
        'read': 'GET'
      }[method];
    
    // Retrieve error message key
    var msgKey = null
    switch(textStatus) {
        case "timeout":
            msgKey = "connectionErrorText-timeout";
        break;
        case "abort":
            msgKey = "connectionErrorText-abort";
        break;
        case "parsererror":
            msgKey = "connectionErrorText-500";
        break;
        case "error":
            if (xhr.status === 0) {
                msgKey = "connectionErrorText-timeout";
            } else {
                var student = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance()
                .get('student');            
                msgKey = "connectionErrorText-"+xhr.status;
                // Custom Model error message handling
                var errorKey = _.result(model,"modelErrorKey", null);
                if (errorKey!=null) {
                    var propMsgKey = httpMethod + "-" + errorKey + "-" + msgKey;
                    // Check if key exists in Language Manager
                    if(LM.getMessage(propMsgKey)!=propMsgKey) {
                        msgKey = propMsgKey;
                    } else {
                        console.error("[edu.kit.informatik.studyplan.client.storage.OAuthSync.errorCallback] Es fehlt das Sprachelement " + propMsgKey)
                    }
                }
            }
        break;
    }
    // Create notification with error message
    var notification = new edu.kit.informatik.studyplan.client.model.system.Notification({
        title: LM.getMessage("connectionErrorTitle"),
        text: LM.getMessage(msgKey),
        wasShown: false,
        type: "error"
    });
    notificationCollection.add(notification);
    edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
}