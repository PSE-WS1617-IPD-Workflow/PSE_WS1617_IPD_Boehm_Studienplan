goog.provide("StudyplanApp.Routers.MainRouter");
/**
 * @constructor
 */
StudyplanApp.Routers.MainRouter = Backbone.Router.extend(/** @lends {StudyplanApp.Routers.MainRouter.prototype}*/{
    routes : {
        "" : "entryPoint"
    },
    entryPoint: function () {
        "use strict";
        console.log("/")
        App.setHeader(StudyplanApp.Views.SubViews.Header, {});
        App.setContent(StudyplanApp.Views.SubViews.Login, {});
        App.render();
    }
});