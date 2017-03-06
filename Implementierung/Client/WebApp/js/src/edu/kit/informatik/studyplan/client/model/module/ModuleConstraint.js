goog.provide("edu.kit.informatik.studyplan.client.model.module.ModuleConstraint");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Model}
 * represents a ModuleConstraint and includes the two modules, a name and the constraint-type.
 */

edu.kit.informatik.studyplan.client.model.module.ModuleConstraint = Backbone.Model.extend( /** @lends {edu.kit.informatik.studyplan.client.model.module.ModuleConstraint.prototype}*/ {
    /**
     *Parses all included information with the parse-method of Module and the standard-parse.s
     */
    parse: function (response, options) {
        //response=response["constraint"];
        response["first"] = new edu.kit.informatik.studyplan.client.model.module.Module({
            module: response["first"]
        }, {
            parse: true
        });
        response["second"] = new edu.kit.informatik.studyplan.client.model.module.Module({
            module: response["second"]
        }, {
            parse: true
        });
        return response;
    },
    
    /**
     * Returns true if the constraint is relevant for the given module id.
     * (In case of type prerequisite constraint is only relevant for second module)
     * @param {string} moduleId The id of the module to check for
     * return {boolean}
     */
    isRelevantFor : function (moduleId) {
        if(this.get('type')=="prerequisite") {
            if(this.get('first').get('id')==moduleId) {
                return false;
            }
        }
        return true;
    }
    
});