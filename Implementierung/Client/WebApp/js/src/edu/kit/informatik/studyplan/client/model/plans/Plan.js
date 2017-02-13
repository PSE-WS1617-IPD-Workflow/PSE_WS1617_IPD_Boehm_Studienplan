goog.provide("edu.kit.informatik.studyplan.client.model.plans.Plan");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 *represents a plan with his modules(in SemesterCollections), name, creditpoints, verificationState and violations(constraints)
 */

edu.kit.informatik.studyplan.client.model.plans.Plan = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.Plan.prototype}*/{
    
    urlRoot : API_DOMAIN + "/plans",
    
    /**
    *parses a plan-JSON and uses for that the parse-methods of SemesterCollection and verification result. Constraints parsed Module.
    */
    parse : function (response, options) {
        "use strict";
        response = response["plan"];
        //included modules aren't passed.
        if(response["modules"]){
            for(var i = 0; i<response["modules"].length; i++){
                response["modules"]["passed"] = false;
            }
        } else {
            response["modules"]=[];
        }
        var student = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('student');
        //includes passed-Modules.
        if(student){
            var passedModules = student.get('passedModules');
        }
        //todo: bleiben die consolen-eingaben?
        console.info("[edu.kit.informatik.studyplan.client.model.plans.Plan] parsing plan")
        console.info("[edu.kit.informatik.studyplan.client.model.plans.Plan] passedModules:")
        console.info(passedModules);
        if(passedModules){
            passedModules.each(function(module){
                module.set('passed', true);
                response["modules"].push(module.toJSON({planModule: true})["module"]);
            });
        }
        // Initialize an object of type client.model.plans.SemesterCollection and set planId and module
        response.semesterCollection = new edu.kit.informatik.studyplan.client.model.plans.SemesterCollection({
            planId : response.id, 
            modules : 
            response.modules
        }, {parse : true, plan: this});
        //building a verificationResult with the given information
        response.verificationResult = new edu.kit.informatik.studyplan.client.model.plans.VerificationResult({
            plan: {
                id: response["id"],
                violations: response["violations"],
                status: response["status"],
                "":response["compulsory-violations"],
                "field-violations": response["field-violations"],
                "rule-group-violations": response["rule-group-violations"]
            }
        },{parse: true, plan: this});
        
        return response;
    },
    /**
     * @param {string|Object=} key
     * @param {*=} value
     * @param {Object=} options
     * @return {boolean|Object}
     * @suppress {checkTypes}
     * saving a Plan with backbone options.
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
    
    /**
    *builds a JSon while using toJSON to semesterCollecton and the standard-JSON
    * with option "post or patch it includes just the name, else id, name and modulecollection.
    */
    toJSON : function (options) {
        if(options.method==="post"){
            return {plan:{name : this.get('name')}};
        } else if (options.method==="patch") {
            return {plan:{id: this.get('id'), name : this.get('name')}};
        } else {
            var result = {
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
    * checks, wheater a module is included. Uses same method of semesterCollection.
    */
    containsModule : function (moduleId) {
        "use strict";
        return this.get("semesterCollection").containsModule(moduleId);
    },
    
    /**
    *sets the parent plan of a new, generated plan.
    */
    retrieveProposedPlan: function () {
        return new edu.kit.informatik.studyplan.client.model.plans.ProposedPlan({
            id: this.get('id'),
            parent: this
        });
    },
    /**
    *
    */
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