goog.provide("edu.kit.informatik.studyplan.client.model.module.Preference");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Model}
 * Preference includes a moduleId, a planId and the setted preference of this module for that plan. 
 * The class includes the reaction to setting the preference.
 */

edu.kit.informatik.studyplan.client.model.module.Preference = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend( /** @lends {edu.kit.informatik.studyplan.client.model.module.Preference.prototype}*/ {

    /**
     *it's the event you use to set the preference of a module
     */
    initialize: function () {
        this.listenTo(this, "change", this.onChange);
    },

    /**
     *trigger if changing.
     */
    onChange: function () {
        this.get('module').trigger('change');
    },
    /**
     * 
     * @return {string}
     */
    url: function () {
        "use strict";
        return _.result(this.get('module'), 'url') + "/preference";
    },
    parse: function (response, options) {
        "use strict";
        //var result = ({planId: response["planId"], value: response["preference"], moduleId: response["id"]});
        var result = response;
        if (this.has('module')) {
            result.module = this.get('module');
        }

        return result;
    },

    /**
     *preference isn't a defined JSon, so we define a Module with given Id and  preference.
     * @param {Object=} options
     * @return {Object}
     */
    toJSON: function (options) {
        "use strict";
        //console.log(this);
        return {
            module: {
                id: this.get('module').get('id'),
                preference: this.get('preference')
            }
        };
    },
    /**
     *Preferences are never 'new' (a.k.a. are always saved with a PUT request)
     */
    isNew: function () {
        "use strict";
        return false;
    }
});