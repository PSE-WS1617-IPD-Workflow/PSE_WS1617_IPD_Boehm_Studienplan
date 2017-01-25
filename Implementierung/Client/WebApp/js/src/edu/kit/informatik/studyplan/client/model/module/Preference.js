goog.provide("edu.kit.informatik.studyplan.client.model.module.Preference");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Model}
 */

edu.kit.informatik.studyplan.client.model.module.Preference = Backbone.Model.extend(/** @lends {edu.kit.informatik.studyplan.client.model.module.Preference.prototype}*/{
    /**
    * @return {string}
    */
    url : function () {
        "use strict";
    },
    parse : function (response, options) {
        "use strict";
        var result = ({planId: response["planId"], value: response["preference"], moduleId: response["id"]});
        return result;
    }
});