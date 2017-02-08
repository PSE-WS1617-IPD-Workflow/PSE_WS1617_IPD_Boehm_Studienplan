goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent");
/**
 * @constructor
 * @extends {Backbone.View} 
 */

edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent = Backbone.View.extend(
    /** @lends {edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.prototype} */{
        
    filter: null,
    
    initialize: function (options) {
        "use strict";
        this.filter = options.filter;
    },
    /**
    *return String
    */
    getId:
        function () {
            "use strict";
        },
    onSelect:
        function () {
            "use strict";
        },
    /**
    *return Filter
    */
    getFilter: function () {
        "use strict";
    },
    render: function () {
        this.$el.html(this.template({filter: this.filter}));
        this.delegateEvents();
    }
});