goog.provide("edu.kit.informatik.studyplan.client.model.module.ModuleCollection");
/**
 * @constructor
 * @param {Object=} response
 * @param {Object=} options
 * @extends {Backbone.Collection}
 */

edu.kit.informatik.studyplan.client.model.module.ModuleCollection = Backbone.Collection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.module.ModuleCollection.prototype}*/{
    planId : null,
    initialize : function (models, options) {
        "use strict";
        if (typeof options !== "undefined" && typeof options.planId !== "undefined") {
            this.planId = options.planId;
        }
    },
    model : edu.kit.informatik.studyplan.client.model.module.Module,
    
    /**
    * @return {string}
    */
    url : function () {
        "use strict";
        throw new Error("[edu.kit.informatik.studyplan.client.model.module.ModuleCollection] The object does not implement the url method.");
    },
    /**
    *
    */
    parse : function (response, options) {
        "use strict";
        var result = [];
        // Initialise modules
        if (typeof response["modules"] !== "undefined") {
            for (var i = 0; i < response["modules"].length; i++) {
                response["modules"][i]["planId"]=this.planId;
                var temp = new this.model({module:response["modules"][i]},{parse:true});
                result.push(temp);
            }
        }
        return result;
    },
    toJSON : function (options) {
        var res = [];
        this.each(function (el) {
            res.push(el.toJSON(options)["module"]);
        })
        return {modules: res};
    },
    /**
    * @param {number} moduleId
    * @return {boolean}
    */
    containsModule : function (moduleId) {
        "use strict";
        if(typeof this.get(moduleId) !== "undefined" /*&& this.get(moduleId).get("id") == moduleId*/) {
            return true;
        } else {
            return false;
        }
    }
});