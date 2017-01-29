goog.provide("edu.kit.informatik.studyplan.client.model.plans.ProposedPlan");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.plans.PlanCollection}
 */

edu.kit.informatik.studyplan.client.model.plans.ProposedPlan = edu.kit.informatik.studyplan.client.model.plans.Plan.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.ProposedPlan.prototype}*/{
    url: function () {
        if (this.proposalInfo) {
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
     */
    setInfo : function (info) {
        this.proposalInfo = info;
    },
    fetch : function (options) {
        return edu.kit.informatik.studyplan.client.model.plans.Plan.apply(this, [options]);
    },
    /**
    * @param {Object} options
    */
    save : function (options) {
        "use strict";
    }
});