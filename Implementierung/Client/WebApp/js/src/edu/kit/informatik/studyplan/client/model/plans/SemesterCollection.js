goog.provide("edu.kit.informatik.studyplan.client.model.plans.SemesterCollection");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Model}
 */
edu.kit.informatik.studyplan.client.model.plans.SemesterCollection = Backbone.Model.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.Plan.prototype}*/{
    planId : null,
    parse : function (response, options) {
        "use strict";
        // Set planId
        this.planId = response["planId"];
        response = response["modules"];
        var semesterModules = [];
        // Sort modules by semester
        for (var i = 0; i<response.length;i++) {
            if(typeof semesterModules[response[i]["semester"]] === "undefined"){
                semesterModules[response[i]["semester"]]=[];
            }
            semesterModules[response[i]["semester"]].push(response[i]);
        }
        var semesters = [];
        // Initialise semesters
        for (var i = 1; i<semesterModules.length; i++) {
            if(typeof semesterModules[i] === "undefined"){
                semesterModules[i]=[];
            }
            semesters[i] = new edu.kit.informatik.studyplan.client.model.plans.Semester({
                planId : this.planId,
                semesterNum : i,
                modules : semesterModules[i]
            },{parse:true});
        }
        return semesters;
    }
});