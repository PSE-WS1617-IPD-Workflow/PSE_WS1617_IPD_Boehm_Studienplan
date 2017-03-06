goog.provide("edu.kit.informatik.studyplan.client.model.module.ModuleCollection");
/**
 * @constructor
 * @param {Object=} response
 * @param {Object=} options
 * @extends {Backbone.Collection}
 * Builds a collection of Modules. For initialization you can give a planId, but don't have to.
 * Semestercollection extends that.
 */

edu.kit.informatik.studyplan.client.model.module.ModuleCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend( /** @lends {edu.kit.informatik.studyplan.client.model.module.ModuleCollection.prototype}*/ {

    //the plan that includes this ModuleCollection.
    planId: null,

    /**
     *
     */
    initialize: function (models, options) {
        "use strict";
        if (typeof options !== "undefined" && typeof options.planId !== "undefined") {
            this.planId = options.planId;
        }
    },
    model: edu.kit.informatik.studyplan.client.model.module.Module,

    /**
     * isn't useable, throws just an error, because we don't need it.
     * @return {string}
     */
    url: function () {
        "use strict";
        throw new Error("[edu.kit.informatik.studyplan.client.model.module.ModuleCollection] The object does not implement the url method.");
    },

    /**
     *parses a JSon (response) from the server to a Javascript-Object to use it in different views. The method starts the parse-Method of included  data classes (modules). 
     */
    parse: function (response, options) {
        "use strict";
        var result = [];
        // Initialise modules
        if (typeof response["modules"] !== "undefined") {
            for (var i = 0; i < response["modules"].length; i++) {
                response["modules"][i]["planId"] = this.planId;
                var temp = new this.model({
                    module: response["modules"][i]
                }, {
                    parse: true
                });
                result.push(temp);
            }
        }
        return result;
    },

    /**
     * toJson converts the Javascript Object ModuleCollection to a Json
     * which can send to the server and can be read by the server.
     * For that it uses the toJSON-method of Module on every included module.
     * @param {Object=} options
     * @return {Object}
     */
    toJSON: function (options) {
        var res = [];
        this.each(function (el) {
            res.push(el.toJSON(options)["module"]);
        })
        return {
            modules: res
        };
    },
    /**
     * The method shows us wheater the moduleCollection includes the module with the given moduleId.
     * If it does, the method  return true.
     * @param {number} moduleId
     * @return {boolean}
     */
    containsModule: function (moduleId) {
        "use strict";
        if (typeof this.get(moduleId) !== "undefined" /*&& this.get(moduleId).get("id") == moduleId*/ ) {
            return true;
        } else {
            return false;
        }
    }
});