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
        //response = response["modules"];
        var result = [];
        // Initialise modules
        if (typeof response.modules !== "undefined"){
        for(var i = 0; i < response.modules.length; i++){
            var temp = new edu.kit.informatik.studyplan.client.model.module.Module(response.modules[i],{parse : true});
            result.push(temp);
        }
    }
        return result;
    }
});