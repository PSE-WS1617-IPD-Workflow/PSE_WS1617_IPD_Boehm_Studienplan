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
    
    initalize : function () {
        "use strict";
        this.render();
        this.headerElement = $("<div id='header'></div>");
        this.$el.append(this.headerElement);
        this.contentElement = $("<div id='content'></div>");
        this.$el.append(this.contentElement);
    },
    
    render : function () {
        "use strict";
        if (this.curHeaderView !== null) {
            this.curHeaderView.render();
        }
        if (this.curContentView !== null) {
            this.curContentView.render();
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
        options["el"] = this.headerElement;
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
        options["el"] = this.contentElement;
        this.curContentView = new Content(options);
    }
});