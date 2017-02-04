goog.provide("edu.kit.informatik.studyplan.client.config.init");
edu.kit.informatik.studyplan.client.config.init = function () {
    "use strict";
    if (DEBUG_ALWAYS_LOGIN) {               
        edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().set('access_token',API_TOKEN);
    }
    $(function () {
        console.info("[edu.kit.informatik.studyplan.client.config.init] Starting Studyplan...");
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance();
        Backbone.history.start({pushState: true});
        console.info("[edu.kit.informatik.studyplan.client.config.init] Backbone.History.started:");
        console.info(Backbone.History.started);
    });
};