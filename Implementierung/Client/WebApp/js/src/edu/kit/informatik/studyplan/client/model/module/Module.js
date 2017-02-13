goog.provide("edu.kit.informatik.studyplan.client.model.module.Module");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Model}
 * Upon construction this class retrieves an object containing the attributes 'planId' and 'module'.
 * Module contains all of the information of the module while planId might contain the name of the plan the module belongs to.
 */

edu.kit.informatik.studyplan.client.model.module.Module = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.module.Module.prototype}*/{
    
    /**
    *Parses a JSon (response) from the server to a Javascript-Object to use it in different views. The method starts the parse-Method of included  data classes ( constraints) , and changes categorie to a parameter, because the client just needs the name of a categorie. It builds an Object of Preference with the given information. Attributes which aren't named, the standard parse-method parses.
    *
    */
    parse : function (response, options) {
        "use strict";
        // Necessary because Model sometimes calls itself with its attributes (intelligent Backbone stuff...)
        if(!response["module"]){
            return response;
        }
        var result = response["module"];
        //this.planId = response["module"]["planId"];
        var categorie = [];
        //don't need more informaiton of categorie, because it's just a shown information for the user.
        if (typeof response["module"]["categories"] !== "undefined") {
            for (var i = 0; i<response["module"]["categories"].length;i++){
                var value = response["module"]["categories"][i].name;
                categorie.push(value);
            }
        }
        result["categories"]=categorie;
        //parses constraints with the parse-method of MoudleCosntraint to get all information.
        var constraint = [];
        if (typeof response["module"]["constraints"] !== "undefined"){
            for(var i = 0;i<response["module"]["constraints"].length; i++){
            var value = new edu.kit.informatik.studyplan.client.model.module.ModuleConstraint(response["module"]["constraints"][i],{parse : true});
            constraint.push(value);
            }
        }
        result["constraints"] = constraint;
        var preferenceInfo = {
            module : this,
            preference : response["module"]["preference"]
        }
        console.log("[edu.kit.informatik.studyplan.client.model.module.Module] preference")
        console.log(preferenceInfo);
        result["preference"]= new edu.kit.informatik.studyplan.client.model.module.Preference(preferenceInfo);
        
        return result;
    },
    
    
    /**
    *toJson converts the Javascript Object Module to a Json which can send to the server and can be read by the server.
    */
    toJSON : function (options) {
        if(options.planModule){
            return {
                module: {
                    id : this.get('id'),
                    semester : this.get('semester'),
                    name : this.get('name'),
                    creditpoints: this.get('creditpoints'),
                    lecturer: this.get('lecturer'),
                    passed: this.get('passed')
                }
            }
        }
        return {
            module : {
                id  :   this.get('id'),
                semester    :   this.get('semester')
            }
        };
    },
    /*
    *Modules are never 'new' (a.k.a. are always saved with a PUT request)
    */
    isNew : function () {
        return false;
    }
});