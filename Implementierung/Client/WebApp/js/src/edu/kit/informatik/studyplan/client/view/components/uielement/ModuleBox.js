goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox} */{
    model: null,
    tagName: "ul",
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/moduleBox.html"),
    
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
        this.$el.html(this.template({notification: this.model}));
        this.delegateEvents();
    }
});