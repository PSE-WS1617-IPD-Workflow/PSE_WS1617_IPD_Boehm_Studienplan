goog.provide("edu.kit.informatik.studyplan.client.view.subview.Header");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.Header = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.Header.prototype} */{
    /** 
    * session information for access permissions
    */
    sessionInformation: null,
    /**
    * the tag name of the described html element in the template
    */
    tagName: "div",
    /**
    * events that trigger certain methods
    */
    events: {
        "click a.profile":"showProfile",
        "click a.logout":"logoutUser",
        "click a.home": "goHome"
    },
    /**
    * html template for this element
    */
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate('resources/templates/subview/header.html'),
    /**
    * initializes the header
    * @this {Backbone.View}
    * @param{...*} options
    *               parameters:
    *                   sessionInformation -> contains the session Information
    */
    initialize: function (options) {
        "use strict";
        this.sessionInformation = options.sessionInformation;
    },
    /*
    * renders the header
    */
    render: function () {
        "use strict";
        this.$el.html(this.template({sessionInformation: this.sessionInformation}));
        this.delegateEvents();
    },
    /**
    * redirects to main page
    * @param{*} event
    */
    goHome: function (event) {
        event.preventDefault();
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/",{trigger:true});
    },
    /**
    * redirects to profile page
    * @param{*} event
    */
    showProfile: function (event) {
        event.preventDefault();
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/profile",{trigger: true});
    },
    /**
    * redirects to logout page
    * @param{*} event
    */
    logoutUser: function (event) {
        event.preventDefault();
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/logout",{trigger: true});
    }
});