goog.provide("edu.kit.informatik.studyplan.client.model.module.Module");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Model}
 * Upon construction this class retrieves an object containing the attributes 'planId' and 'module'.
 * Module contains all of the information of the module while planId might contain the name of the plan the module belongs to.
 */

edu.kit.informatik.studyplan.client.model.module.Module = Backbone.Model.extend(/** @lends {edu.kit.informatik.studyplan.client.model.module.Module.prototype}*/{
    planId : null,
    /**
    * Achtung: in jSon doku fehlt compulsory. 
    *Plan iD wird von Plan durchgegeben, falls Modul zu Plan gehört. Kategorie ist kein eigenes Objekt. 
    */
    parse : function (response, options) {
        "use strict";
        this.planId = response["planId"];
        var categories = [];
        for (var i = 0; i<response["categories"].length;i++){
            categories.push(response.categories[i].name);
        }
        response["categories"]=this.categories;
        var constraints = [];
        for(var i = 0;i<response["constraints"].length; i++){)
        this.constraints.push(new edu.kit.informatik.studyplan.client.model.module.ModuleConstraint({response.constraints[i]},{parse : true}));
        }
        response.constraints = this.constraints;
        
        response["preference"]=new edu.kit.informatik.studyplan.client.model.module.Preference({response,{parse: true});
        return response;
    }
});