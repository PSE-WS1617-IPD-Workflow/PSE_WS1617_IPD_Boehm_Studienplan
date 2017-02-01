goog.provide("edu.kit.informatik.studyplan.client.view.MainView");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.MainView = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.MainView.prototype} */{
    el: "body",
    curHeaderView : null,
    curContentView : null,
    headerElement : null,
    contentElement: null,
    
    initialize : function () {
        "use strict";
        console.info("[edu.kit.informatik.studyplan.client.view.MainView] initializing...")
        this.$el.html('');
        this.headerElement = $("<div id='header'></div>");
        this.$el.append(this.headerElement);
        this.contentElement = $("<div id='content'></div>");
        this.$el.append(this.contentElement);
        this.render();
    },
    
    render : function () {
        "use strict";
        if (this.curHeaderView !== null) {
            this.curHeaderView.render();
            this.headerElement.html('');
            this.headerElement.append(this.curHeaderView.$el);
        }
        if (this.curContentView !== null) {
            this.curContentView.render();
            this.contentElement.html('');
            this.contentElement.append(this.curContentView.$el);
        }
        this.$el.show();
    },
    /**
    *@param{function(new:Backbone.View, Object)} Header
    *@param{Object} options
    */
    setHeader : function (Header, options) {
        "use strict";
        if (this.curHeaderView !== null) {
            this.curHeaderView.remove();
        }
        this.curHeaderView = new Header(options);
    },
    /**
    *@param{function(new:Backbone.View, Object)} Content
    *@param{Object} options
    */
    setContent : function (Content, options) {
        "use strict";
        if (this.curContentView !== null) {
            this.curContentView.remove();
        }
        this.curContentView = new Content(options);
    }
});