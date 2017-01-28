goog.provide("edu.kit.informatik.studyplan.client.model.plans.Plan");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 */

edu.kit.informatik.studyplan.client.model.plans.Plan = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.Plan.prototype}*/{
    urlRoot : API_DOMAIN + "/plans",
    
    parse : function (response, options) {
        "use strict";
        response = response["plan"];
        if(response["modules"]){
            for(var i = 0; i<response["modules"].length; i++){
                response["modules"]["passed"] = false;
            }
        } else {
            response["modules"]=[];
        }
        var student = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('student');
        if(student){
            var passedModules = student.get('passedModules');
        }
        if(passedModules){
            passedModules.forEach(function(module){
                module["passed"] = true;
                response["modules"].push(module);
            });
        }
        // Initialise an object of type client.model.plans.SemesterCollection and set planId and module
            // TODO: Inject modules from Student.getInstance().get('passedModules');
        response.semesterCollection = new edu.kit.informatik.studyplan.client.model.plans.SemesterCollection({planId : response.id, modules : response.modules}, {parse : true});
        // Check if violations exist, if so add them
        if (typeof response["violations"] === "undefined") {
            response["violations"]=[];   
        }
        var violations = [];
        for (var i = 0; i < response["violations"].length; i++) {
            violations.push(new edu.kit.informatik.studyplan.client.model.module.ModuleConstraint(response["violations"][i],{parse:true}));
        }
        response["violations"] = violations;
        return response;
    },
    /**
     * @param {string|Object=} key
     * @param {*=} value
     * @param {Object=} options
     * @return {boolean|Object}
     * @suppress {checkTypes}
     */
    save : function (key, value, options){
        var attrs;
        if (key == null || typeof key === 'object') {
            attrs = key;
            options = value;
        } else {
            (attrs = {})[key] = value;
        }
        if (typeof options === "undefined") {
            options = {};
        }
        _.defaults(options,{
            patch : true,
        });
        if(this.isNew()){
            options["method"]="post";
        } else {
            if(options.patch==true){
                options["method"]="patch";
            } else {
                options["method"]="put";
            }
        }
        return Backbone.Model.prototype.save.apply(this,[attrs, options]);
    },
    toJSON : function (options) {
        if(options.method==="post"){
            return {plan:{name : this.get('name')}};
        }else if(options.method==="patch"){
            return {plan:{id : this.id, name : this.get('name')}};
        } else {
            var result = {
                id: this.id,
                name: this.get('name'),
            };
            if(this.get('semesterCollection')){
                result.modules = this.get('semesterCollection').toJSON(options);
            }
            return {plan:result};
        }
    },
    /**
    * @param {number} moduleId
    * @return {boolean}
    */
    containsModule : function (moduleId) {
        "use strict";
    },
    /**
    *
    */
    loadVerification : function () {
        "use strict";
    }
});