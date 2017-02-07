goog.provide("edu.kit.informatik.studyplan.client.model.plans.Plan");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
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
        response.semesterCollection = new edu.kit.informatik.studyplan.client.model.plans.SemesterCollection({planId : response.id, modules : response.modules}, {parse : true});
        response.verificationResult = new edu.kit.informatik.studyplan.client.model.plans.VerificationResult({
            plan: {
                id: response["id"],
                violations: response["violations"],
                status: response["status"]
            }
        },{parse: true});
        
        var fieldViolations = [];
        if(typeof response["field-violations"] !== "undefined") {
            for(var i = 0; i<response["field-violations"].length; i++){
                fieldViolations[i] = new edu.kit.informatik.studyplan.client.model.system.Field(
                      response["field-violations"][i], {parse: true});
            }
        }
        var ruleGroupViolations = [];
        if(typeof response["rule-group-violations"] !== "undefined") {
            for(var i = 0; i<response["rule-group-violations"].length; i++){
               ruleGroupViolations[i] = new edu.kit.informatik.studyplan.client.model.plans.RuleGroup(
                      response["rule-group-violations"][i], {parse: true});
            }
        }
        response["field-violations"] = fieldViolations;
        response["rule-group-violations"] = ruleGroupViolations; 
        
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
        if(options.method==="post" || options.method==="patch"){
            return {plan:{name : this.get('name')}};
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
        return this.get("semesterCollection").containsModule(moduleId);
    },
    retrieveProposedPlan: function () {
        return new edu.kit.informatik.studyplan.client.model.plans.ProposedPlan({
            id: this.get('id'),
            parent: this
        });
    }
});