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
        console.info("[edu.kit.informatik.studyplan.client.model.plans.Plan] parsing plan")
        if(passedModules){
            passedModules.each(function(module){
                module.set('passed', true);
                response["modules"].push(module.toJSON({planModule: true})["module"]);
            });
        }
        // Initialise an object of type client.model.plans.SemesterCollection and set planId and module
        response.semesterCollection = new edu.kit.informatik.studyplan.client.model.plans.SemesterCollection({
            planId : response.id, 
            modules : 
            response.modules
        }, {parse : true, plan: this});
        
        response.verificationResult = new edu.kit.informatik.studyplan.client.model.plans.VerificationResult({
            plan: {
                id: response["id"],
                violations: response["violations"],
                status: response["status"],
                "":response["compulsory-violations"],
                "field-violations": response["field-violations"],
                "rule-group-violations": response["rule-group-violations"]
            }
        },{parse: true});
        
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
    },
    getEctsSum: function () {
        return this.get('semesterCollection').getEctsSum();
    },
    
    /**
    * Ignores if a semester was passed
    */
    addModule: function (module) {
        this.get("semesterCollection").addModule(module);
    }
});