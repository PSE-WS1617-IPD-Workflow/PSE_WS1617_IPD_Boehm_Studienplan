goog.provide("edu.kit.informatik.studyplan.client.model.module.ModuleCollection");
/**
 * @constructor
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
                var temp = new this.model(response["modules"][i]);
                
                result.push(temp);
            }
        }
        return result;
    }
});