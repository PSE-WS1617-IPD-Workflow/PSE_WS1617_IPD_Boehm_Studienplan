goog.provide("edu.kit.informatik.studyplan.client.model.plans.Plan");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 */

edu.kit.informatik.studyplan.client.model.plans.Plan = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.Plan.prototype}*/{
    urlRoot : API_DOMAIN + "/plans",
    
    parse : function (response, options) {
        "use strict";
        for(var i = 0; i<response.modules; i++){
            response.modules.passed = false;
        }
        var student = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('student');
        if(student){
            var passedModules = student.get('passedModules');
        }
        if(passedModules){
            passedModules.forEach(function(module){
                module.passed = true;
                response.modules.push(module);
            });
        }
        // Check if plan modules were given in response
        if (typeof response.modules !== "undefined") {
            // Initialise an object of type client.model.plans.SemesterCollection and set planId and module
            // TODO: Inject modules from Student.getInstance().get('passedModules');
            response.semesterCollection = new edu.kit.informatik.studyplan.client.model.plans.SemesterCollection({planId : response.id, modules : response.modules}, {parse : true});
        }
        // Check if violations exist, if so add them
        if (typeof response.violations !== "undefined") {
            var violations = [];
            for (var i = 0; i < response.violations; i++) {
                violations.push(new edu.kit.informatik.studyplan.client.model.module.ModuleConstraint(response.violations[i],{parse:true}));
            }
            response.violations = violations;
        }
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
        _.defaults(options,{
            patch : true,
        });
        if(this.isNew()){
            options["method"]="create";
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
        if(options.method==="create"||options.method==="patch"){
            return {id : this.id, name : this.get('name')};
        } else {
            var result = {
                id: this.id,
                status: this.get('status'),
                "creditpoints-sum":this.get('creditpoints-sum'),
                "name": this.get('name'),
            };
            result.violations = [];
            var violations = this.get('violations');
            if(violations){
                for(var i = 0; i<violations.length; i++){
                    result.violations.push(violations[i].toJSON(options));
                }
            }
            if(this.get('semesterCollection')){
                result.modules = this.get('semesterCollection').toJSON(options);
            }
            return result;
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