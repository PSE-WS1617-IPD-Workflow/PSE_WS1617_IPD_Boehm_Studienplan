goog.provide("edu.kit.informatik.studyplan.client.view.subview.Header");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.Header = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.Header.prototype} */{
    sessionInformation: null,
    tagName: "div",
    events: {
        "click a.profile":"showProfile",
        "click a.logout":"logoutUser",
        "click a.home": "goHome"
    },
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate('resources/templates/subview/header.html'),
    initialize: function (options) {
        "use strict";
        this.sessionInformation = options.sessionInformation;
    },
    render: function () {
        "use strict";
        this.$el.html(this.template({sessionInformation: this.sessionInformation}));
        this.delegateEvents();
    },
    goHome: function (event) {
        event.preventDefault();
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/",{trigger:true});
    },
    showProfile: function (event) {
        event.preventDefault();
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/profile",{trigger: true});
    },
    logoutUser: function (event) {
        event.preventDefault();
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/logout",{trigger: true});
    }
});