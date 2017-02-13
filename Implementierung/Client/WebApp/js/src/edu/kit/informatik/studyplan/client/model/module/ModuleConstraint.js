goog.provide("edu.kit.informatik.studyplan.client.model.module.ModuleConstraint");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Model}
 * represents a ModuleConstraint and includes the two modules, a name and the constraint-type.
 */

edu.kit.informatik.studyplan.client.model.module.ModuleConstraint = Backbone.Model.extend(/** @lends {edu.kit.informatik.studyplan.client.model.module.ModuleConstraint.prototype}*/{
    /**
    *Parses all included information with the parse-method of Module and the standard-parse.s
    */
    parse : function (response, options) {
        //response=response["constraint"];
        response["first"]=new edu.kit.informatik.studyplan.client.model.module.Module({module:response["first"]}, {parse: true});
        response["second"]= new edu.kit.informatik.studyplan.client.model.module.Module({module: response["second"]}, {parse: true});
        return response;
    }
});