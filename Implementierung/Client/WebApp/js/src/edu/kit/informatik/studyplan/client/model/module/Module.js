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
    parse : function (response, options){
        this.planId = response["planId"];
        // TODO: process response["module"];
    }
});