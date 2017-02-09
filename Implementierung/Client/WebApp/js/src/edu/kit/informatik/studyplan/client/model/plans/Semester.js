goog.provide("edu.kit.informatik.studyplan.client.model.plans.Semester");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Collection}
 */

edu.kit.informatik.studyplan.client.model.plans.Semester = edu.kit.informatik.studyplan.client.model.module.ModuleCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.Semester.prototype}*/{
    planId : null,
    semesterNum : 0,
    url : function () {
        return API_DOMAIN + "plans/"+this.planId+"/modules"
    },
    parse : function (response, options) {
        "use strict";
        this.planId = response["planId"];
        this.semesterNum = response["semesterNum"];
        
        // Initialise modules
        for(var i = 0; i < response["modules"].length; i++){
            response["modules"][i]["semester"] = this.semesterNum;
        }
        //console.log(response["modules"]);
        return edu.kit.informatik.studyplan.client.model.module.ModuleCollection.prototype.parse.apply(this,[response,options]);
    },
    getEctsSum: function () {
        var sum = 0;
        this.each(function (module) {
            sum+=module.get('creditpoints');
        });
        return sum;
    }
});