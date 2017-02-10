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
    initialize: function (options) {
        this.model=options.module;
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
        this.$el.html(this.template({module: this.model}));
        this.$el.draggable({
            scroll: true,
            opacity: 0.7,
            helper: "clone",
            start: function (event, ui){
                ui.helper.data("modelObject", this.model);
                ui.helper.addClass("grabbing");
            }.bind(this)
        });
        this.delegateEvents();
    }
});