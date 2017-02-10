goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox.prototype} */{
    model: null,
    tagName: "li",
    className: "moduleBox",
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/moduleBox.html"),
    isPreferencable: true,
    isDraggable: true,
    isDeletable: true,
    isPassedPlanModule: false,
    initialize: function (options) {
        this.model=options.module;
        this.isRemovable = options.isRemovable;
        this.isDraggable = options.isDraggable;
        this.isPreferencable = options.isPreferencable;
        this.isPassedPlanModule = options.isPassedPlanModule;
    },
    
    /**
    *@param{boolean} setBorder
    */
    setRedBorder:
        function (setBorder) {
            "use strict";
        },
    /**
    *
    */
    removeModule:
        function () {
            "use strict";
        },
    /**
    *
    */
    click:
        function () {
            "use strict";
        },
    /**
    *
    */
    render: function () {
        this.$el.addClass((this.isDraggable) ? 'draggable' : '');
        this.$el.html(this.template({
            module: this.model,
            isRemovable : this.isRemovable,
            isDraggable : this.isDraggable,
            isPreferencable : this.isPreferencable
        }));
        if(this.isDraggable){
            this.$el.draggable({
                scroll: true,
                opacity: 0.7,
                helper: "clone",
                appendTo: 'body',
                containment: 'window',
                start: function (event, ui){
                    ui.helper.data("viewObject", this);
                    ui.helper.addClass("grabbing");
                }.bind(this)
            });
        }
        this.delegateEvents();
    }
});