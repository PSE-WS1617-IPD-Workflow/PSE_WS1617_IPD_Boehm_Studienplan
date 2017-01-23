goog.provide("edu.kit.informatik.studyplan.client.model.plans.Semester");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Collection}
 */

edu.kit.informatik.studyplan.client.model.plans.Semester = Backbone.Collection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.Semester.prototype}*/{
    planId : null,
    semesterNum : 0,
    parse : function (response, options) {
        "use strict";
        // Set planId
        this.planId = response["planId"];
        this.semesterNum = response["semesterNum"];
        response = response["modules"];
        var result = [];
        // Initialise modules
        for(var i = 0; i < response.length; i++){
            result.push(new edu.kit.informatik.studyplan.client.model.module.Module({planId : this.planId, module : response[i]},{parse : true, collection : this}));
        }
        return result;
    }
});