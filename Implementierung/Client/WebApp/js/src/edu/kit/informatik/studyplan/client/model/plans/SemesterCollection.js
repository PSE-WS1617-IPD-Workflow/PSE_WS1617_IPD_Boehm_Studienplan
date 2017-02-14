goog.provide("edu.kit.informatik.studyplan.client.model.plans.SemesterCollection");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Model}
 */
edu.kit.informatik.studyplan.client.model.plans.SemesterCollection = Backbone.Model.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.SemesterCollection.prototype}*/{
    length: 0,
    planId : null,
    initialize: function (attributes, options) {
        this.plan = options.plan;
        this.listenTo(this, "change", this.onChange);
    },
    onChange: function () {
        this.plan.trigger("change");
    },
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
            },{parse:true, collection: this});
        }
        var student = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance()
            .get('student');
        if (student) {
            var curSem = student.get('current-semester');
        }
        var semesterNum = (curSem) ? curSem : 1;
        while(semesters.length<=semesterNum){
            semesters[semesters.length+1] = new edu.kit.informatik.studyplan.client.model.plans.Semester({
                    planId : this.planId,
                    semesterNum : (semesters.length+1),
                    modules : []
            },{parse:true, collection: this})
            this.length = semesterNum;
        }
        this.length = semesters.length;
        if(semesters.length<semesterNum){
            for(var i = (semesters.length+1); i<=semesterNum; i++){
                
            }
        }
        return semesters;
    },
    toJSON : function (options){
        var result = [];
        _.each(this.attributes, function (el) {
            result = _.union(el.toJSON(options)["modules"],result);
        });
        return result;
    },
        /**
    * @param {number} moduleId
    * @return {boolean}
    */
    containsModule : function (moduleId) {
        "use strict";
        var contained = false;
        _.each(this.attributes, function (element) {
            if(element.containsModule(moduleId)) {
                contained = true;
            }
        } );
        return contained;
    },
    each: function (method) {
        _.each(this.attributes, method);
    },
    getEctsSum: function () {
        var sum = 0;
        this.each(function (semester) {
            sum+=semester.getEctsSum();
        });
        return sum;
    },
    addModule: function (module) {
        var i = module.get("semester");
        if(!this.get(i)) {
            this.set(i, new edu.kit.informatik.studyplan.client.model.plans.Semester({
                planId : this.planId,
                semesterNum : module.get("semester"),
                modules : []
            },{parse:true, collection: this}));
        }
        this.get(i).push(module);
    },
    push : function (semester){
        this.attributes[this.length + 1] = semester;
        this.length++;
    }
});