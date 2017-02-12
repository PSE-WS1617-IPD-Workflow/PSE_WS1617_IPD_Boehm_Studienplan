goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/moduleList.html"),
    moduleCollection: null,
    tagName: "ul",
    moduleBoxes: null,
    initialize: function(options){
        "use strict";
        this.moduleCollection = options.moduleCollection;
        this.isDraggable = options.isDraggable;
        this.isRemovable = options.isRemovable;
        this.planId = options.planId;
        console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList] planId: " + this.planId);
        this.isPreferencable = options.isPreferencable;
        //this.listenTo(this.moduleCollection, "change", this.onChange);
        //this.listenTo(this.moduleCollection, "all", this.onChange);
        this.listenTo(this.moduleCollection, "reset", this.onChange);
        //this.listenTo(this.moduleCollection, "add", this.onChange);
        this.reload();
    },
    reload: function () {
        this.moduleBoxes = [];
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        var self = this;
        this.moduleCollection.each(function (module) {
            var tmpModuleBox = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox({
                module: module,
                //TODO: einstellbar
                isRemovable: this.isRemovable,
                isDraggable: this.isDraggable,
                isPreferencable: this.isPreferencable,
            });
            self.moduleBoxes.push(tmpModuleBox);
        }.bind(this));
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
        this.render();
    },
    onChange: function (){
        this.reload();
    },
    render: function () {
        this.$el.html(this.template());
        var container = this.$el.find(".moduleListWrapper");
        _.each(this.moduleBoxes, function(el){
            el.render();
            container.append(el.$el)
        }.bind(this));
        this.delegateEvents();
    }
});