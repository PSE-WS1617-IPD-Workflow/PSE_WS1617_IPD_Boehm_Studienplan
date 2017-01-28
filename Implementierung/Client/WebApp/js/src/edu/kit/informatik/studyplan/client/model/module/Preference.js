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
        return _.result(this.get('module'), 'url') + "/preference";
    },
    /*parse : function (response, options) {
        "use strict";
        var result = ({planId: response["planId"], value: response["preference"], moduleId: response["id"]});
        return result;
    },*/
    toJSON : function (options) {
        "use strict";
        return {
            module : {
                id  :   this.get('module').get('id'),
                preference :    this.get('preference')
            }
        };
    },
    // Preferences are never 'new' (a.k.a. are always saved with a PUT request)
    isNew : function () {
        "use strict";
        return false;
    }
});