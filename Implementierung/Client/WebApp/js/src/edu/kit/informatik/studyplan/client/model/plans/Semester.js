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
    url : function () {
        return API_DOMAIN+"plans/"+this.planId+"/modules"
    },
    parse : function (response, options) {
        "use strict";
        // Set planId
        this.planId = response["planId"];
        this.semesterNum = response["semesterNum"];
        response = response["modules"];
        var result = [];
        
        // Initialise modules
        for(var i = 0; i < response.length; i++){
            _.extend(response[i],{
                planId : this.planId,
                semester : this.semesterNum
            });
            result.push(new edu.kit.informatik.studyplan.client.model.module.Module({module : response[i]},{parse : true, collection : this}));
        }
        return result;
    }
});