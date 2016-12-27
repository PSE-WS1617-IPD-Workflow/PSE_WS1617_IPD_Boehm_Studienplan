goog.provide("StudyplanApp.Views.AppView");
/**
 * Main View of App
 * @constructor 
 * @extends {Backbone.View}
 */
StudyplanApp.Views.AppView = Backbone.View.extend(/** @lends {StudyplanApp.Views.AppView.prototype}*/ {
    el: "body",
    template: StudyplanApp.Templating.TemplateRegistry.getInstance().getTemplate("templates/wrapper.html"),
    curHeaderView : null,
    curContentView : null,
    
    initialize : function () {
        "use strict";
        this.render();
        this.headerElement = $("<div id='header'></div>");
        this.$el.append(this.headerElement);
        this.contentElement = $("<div id='content'></div>");
        this.$el.append(this.contentElement);
    },
    
    render : function () {
        "use strict";
        if(this.curHeaderView!==null){
            this.curHeaderView.render();
        }
        if(this.curContentView!==null){
            this.curContentView.render();
        }
        this.$el.show();
        /*if(this.curHeaderView!==null){
            this.$el.find("#header").replaceWith(this.curHeaderView.render());
        }
        if(this.curContentView!==null){
            this.$el.find("#content").replaceWith(this.curContentView.render());
        }*/
    },
    /**
     * @param {function(new:Backbone.View, Object)} View
     * @param {Object<string, Object | string>} options
     */
    setHeader : function (View, options) {
        "use strict";
        if(this.curHeaderView!==null){
            this.curHeaderView.remove();
        }
        options["el"]=this.headerElement
        this.curHeaderView = new View(options);
    },
    /**
     * @param {function(new:Backbone.View, Object)} View
     * @param {Object<string, Object | string>} options
     */
    setContent : function (View, options) {
        "use strict";
        if(this.curContentView!==null){
            this.curContentView.remove();
        }
        options["el"]=this.contentElement;
        this.curContentView = new View(options);
        //this.curContentView.setElement(this.$el.find("#content"));
    }
});