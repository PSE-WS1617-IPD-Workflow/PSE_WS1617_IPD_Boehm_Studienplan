goog.provide("edu.kit.informatik.studyplan.client.model.plans.ProposedPlan");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.plans.PlanCollection}
 * It's the result of generation.
 */

edu.kit.informatik.studyplan.client.model.plans.ProposedPlan = edu.kit.informatik.studyplan.client.model.plans.Plan.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.ProposedPlan.prototype}*/{
    
    /**
    *
    */
    url: function () {
        if (!this.proposalInfo) {
            throw new Error("[edu.kit.informatik.studyplan.client.model.plans.ProposedPlan] no proposal information");
        }
        return _.result(this.get('parent'),'url') + "/proposal/" + this.proposalInfo.get('objectiveFunction').get('id');
    },
    /**
     * @type {edu.kit.informatik.studyplan.client.model.system.ProposalInformation}
     */
    proposalInfo : null,
    /**
     * @param {edu.kit.informatik.studyplan.client.model.system.ProposalInformation} info
     * set proposedInformationn (used in Generationwizzard, to collect the information for generating a plan.)
     */
    setInfo : function (info) {
        this.proposalInfo = info;
    },
    /**
    *
    */
    fetch : function (options) {
        if(typeof options === "undefined"){
            options = {};
        }
        if(typeof options["data"] === "undefined"){
            options["data"]={};
        }
        options["data"]["min-semesters"]=this.proposalInfo.get("min-semesters");
        options["data"]["max-semesters"]=this.proposalInfo.get("max-semesters");
        options["data"]["min-semester-ects"]=this.proposalInfo.get("min-semester-ects");
        options["data"]["max-semester-ects"]=this.proposalInfo.get("max-semester-ects");
        var fields = "";
        this.proposalInfo.get('fieldCollection').each(function (field) {
            fields += ((fields!=="") ? "," : "")+field.get("id");
            options["data"]["field-"+field.get("id")]=field.get("curValue");
        });
        options["data"]["fields"]=fields;
        return edu.kit.informatik.studyplan.client.model.plans.Plan.prototype.fetch.apply(this, [options]);
    },
    /**
    * @param {Object} options
    * @return {edu.kit.informatik.studyplan.client.model.plans.Plan} The plan in which the proposal was saved
    */
    getPlan : function (options) {
        "use strict";
        if(options.newPlan){
            //new plan mighed be initialized and saved.
            var plan = new edu.kit.informatik.studyplan.client.model.plans.Plan({name: options["planName"]});
            plan.set('id',undefined);
            var self = this;
            plan.save(null,{
                success: function () {
                    self.set('name',plan.get('name'));
                    self.set('id', plan.get('id'));
                    plan.attributes = self.attributes;
                    options["patch"] = false;
                    plan.save(null,options);
                }
            });
            
            return plan;
        } else {
            //
            this.get('parent').attributes=_.extend(this.get('parent').attributes,this.attributes);
            options["patch"]=false;
            this.get('parent').save(null, options);
            return this.get('parent');
        }
    }
});