goog.provide("edu.kit.informatik.studyplan.client.view.MainView");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.MainView = Backbone.View.extend( /** @lends {edu.kit.informatik.studyplan.client.view.MainView.prototype} */ {
    el: "body",
    curHeaderView: null,
    curContentView: null,
    notificationCentre: null,
    headerElement: null,
    contentElement: null,
    notificationElement: null,
    showLoad: false,
    loading: $(edu.kit.informatik.studyplan.client.model.system.TemplateManager
        .getInstance()
        .getTemplate("resources/templates/subview/loading.html")()),
    initialize: function () {
        "use strict";
        //console.info("[edu.kit.informatik.studyplan.client.view.MainView] initializing...");
        this.headerElement = $("<div id='header'></div>");
        this.contentElement = $("<div id='content'></div>");
        this.notificationElement = $("<div id='notifications'></div>");
        this.notificationCentre = new edu.kit.informatik.studyplan.client.view.components.uipanel.NotificationCentre();
        this.render();
    },

    render: function () {
        "use strict";
        if (this.curHeaderView !== null) {
            this.curHeaderView.remove();
        }
        if (this.curContentView !== null) {
            this.curContentView.remove();
        }
        this.$el.html('');
        this.headerElement.html('');
        this.$el.append(this.headerElement);
        this.contentElement.html('');
        this.$el.append(this.contentElement);
        this.notificationElement.html('');
        this.$el.append(this.notificationElement);
        if (this.curHeaderView !== null) {
            this.curHeaderView.render();
            this.headerElement.append(this.curHeaderView.$el);
        }
        if (this.curContentView !== null) {
            this.curContentView.render();
            this.contentElement.append(this.curContentView.$el);
        }
        this.notificationCentre.render();
        this.notificationElement.append(this.notificationCentre.$el);
        this.$el.append(this.loading);
        
        this.$el.show();
        this.delegateEvents();
    },
    showLoading: function () {
        var old = this.showLoad;
        this.showLoad = true;
        if (old != this.showLoad) {
            $(".loadingScreen").css({display:"block"});
        }
    },
    hideLoading: function () {
        var old = this.showLoad;
        this.showLoad = false;
        if (old != this.showLoad) {
            $(".loadingScreen").css({display:"none"});
        }
    },
    /**
     *@param{function(new:Backbone.View, Object)} Header
     *@param{Object} options
     */
    setHeader: function (Header, options) {
        "use strict";
        if (this.curHeaderView !== null) {
            this.curHeaderView.remove();
        }
        options["tourHandler"] = this.tourHandler.bind(this);
        this.curHeaderView = new Header(options);
    },
    /**
     *@param{function(new:Backbone.View, Object)} Content
     *@param{Object} options
     */
    setContent: function (Content, options) {
        "use strict";
        if (this.curContentView !== null) {
            this.curContentView.remove();
        }
        this.curContentView = new Content(options);
    },
    /**
     * Function which handles the view of tours when someone clicks on the help button
     */
    tourHandler: function () {
        if(!this.curContentView.pageTour||this.curContentView.pageTour==null) {
            var notification = new edu.kit.informatik.studyplan.client.model.system.Notification({
                title: LM.getMessage("noHelpAvailableTitle"),
                text: LM.getMessage("noHelpAvailableText"),
                wasShown: false,
                type: ""
            });
            edu.kit.informatik.studyplan.client.model.system.NotificationCollection
                        .getInstance().add(notification);
            return;
        }
        this.curContentView.pageTour().start();
    }
});