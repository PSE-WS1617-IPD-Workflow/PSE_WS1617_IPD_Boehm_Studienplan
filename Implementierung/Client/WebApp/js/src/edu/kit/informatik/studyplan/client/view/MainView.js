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
        this.headerElement = $("<div id='header'></div>");
        this.contentElement = $("<div id='content'></div>");
        this.render();
    },
    
    render : function () {
        "use strict";
        this.$el.html('');
        this.$el.append(this.headerElement);
        this.headerElement.html('');
        this.$el.append(this.contentElement);
        this.contentElement.html('');
        if (this.curHeaderView !== null) {
            this.curHeaderView.render();
            this.headerElement.html('');
            this.headerElement.append(this.curHeaderView.$el);
        }
        if (this.curContentView !== null) {
            this.curContentView.render();
            console.log(this.contentElement.html());
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