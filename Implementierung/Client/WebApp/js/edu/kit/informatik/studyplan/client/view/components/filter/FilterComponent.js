goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent");
/**
 * @constructor
 * @ abstract 
 */

edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent = {
    /**
    *return String
    */
    getId:
        function () {
            "use strict";
        },
    /**
    *@abstract
    */
    onSelect:
        function () {
            "use strict";
        },
    /**
    *return Filter
    */
    getFilter: function () {
        "use strict";
    }
};