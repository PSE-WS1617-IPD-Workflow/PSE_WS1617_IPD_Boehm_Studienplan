goog.provide("edu.kit.informatik.studyplan.client.model.module.ModuleConstraint");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Model}
 */

edu.kit.informatik.studyplan.client.model.module.ModuleConstraint = Backbone.Model.extend(/** @lends {edu.kit.informatik.studyplan.client.model.module.ModuleConstraint.prototype}*/{
    parse : function (response, options) {
        //response=response["constraint"];
        response["first"]=new edu.kit.informatik.studyplan.client.model.module.Module({module:response["first"]}, {parse: true});
        response["second"]= new edu.kit.informatik.studyplan.client.model.module.Module({module: response["second"]}, {parse: true});
        return response;
    }
});