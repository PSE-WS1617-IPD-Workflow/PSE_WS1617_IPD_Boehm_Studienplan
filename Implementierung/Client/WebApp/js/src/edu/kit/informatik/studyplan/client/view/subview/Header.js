goog.provide("edu.kit.informatik.studyplan.client.view.subview.Header");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.Header = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.Header.prototype} */{
    sessionInformation: null,
    tagName: "div",
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate('resources/templates/subview/header.html'),
    initialize: function (options) {
        "use strict";
        this.sessionInformation = options.sessionInformation;
    },
    render: function () {
        "use strict";
        this.$el.html(this.template({sessionInformation: this.sessionInformation}));
    }
});