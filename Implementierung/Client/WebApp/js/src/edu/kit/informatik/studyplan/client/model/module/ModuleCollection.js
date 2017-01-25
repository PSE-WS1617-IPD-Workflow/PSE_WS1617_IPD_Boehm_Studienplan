goog.provide("edu.kit.informatik.studyplan.client.model.module.ModuleCollection");
/**
 * @constructor
 * @extends {Backbone.Collection}
 */

edu.kit.informatik.studyplan.client.model.module.ModuleCollection = Backbone.Collection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.module.ModuleCollection.prototype}*/{
    
    /**
    * @return {string}
    */
    url : function () {
        "use strict";
    },
    /**
    *
    */
    parse : function (response, options) {
        "use strict";
        response = response["modules"];
        var result = [];
        // Initialise modules
        for(var i = 0; i < response.length; i++){
            result.push(new edu.kit.informatik.studyplan.client.model.module.Module({module : response[i]},{parse : true, collection : this}));
        }
        return result;
    }
});