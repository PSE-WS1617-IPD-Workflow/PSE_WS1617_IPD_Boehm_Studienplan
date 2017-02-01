goog.provide("edu.kit.informatik.studyplan.client.config.init");
edu.kit.informatik.studyplan.client.config.init = function () {
    console.info("[edu.kit.informatik.studyplan.client.config.init] Starting Studyplan...")
    new edu.kit.informatik.studyplan.client.router.MainRouter();
    Backbone.history.start({pushState: true});
};