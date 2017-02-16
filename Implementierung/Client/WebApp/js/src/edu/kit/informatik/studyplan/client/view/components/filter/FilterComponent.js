goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View} 
 * a general filter class (like abstract parent class)
 */

edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent = Backbone.View.extend(
    /** @lends {edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.prototype} */
    {
        className: "filterComponent",
        filter: null,

        /**
         *constructor: needs a detialed filter as parameter
         */
        initialize: function (options) {
            "use strict";
            this.filter = options.filter;
        },
        /**
         *just something like an abstract method
         *return String
         */
        getId: function () {
            "use strict";
        },
        /**
         *just something like an abstract method
         */
        onSelect: function () {
            "use strict";
        },
        /**
         *just something like an abstract method
         *return Filter
         */
        getFilter: function () {
            "use strict";
        },
        /**
         *jrendering the template
         */
        render: function () {
            "use strict";
            this.$el.html(this.template({
                filter: this.filter
            }));
            this.delegateEvents();
        }
    }
);