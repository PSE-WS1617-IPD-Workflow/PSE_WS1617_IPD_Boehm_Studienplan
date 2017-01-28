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
        this.semesterNum = response["semesterNum"];
        
        // Initialise modules
        for(var i = 0; i < response["modules"].length; i++){
            _.extend(response[i],{
                semester : this.semesterNum
            });
        }
        return edu.kit.informatik.studyplan.client.model.module.ModuleCollection.prototype.parse.apply(this,[response,options]);
    }
});