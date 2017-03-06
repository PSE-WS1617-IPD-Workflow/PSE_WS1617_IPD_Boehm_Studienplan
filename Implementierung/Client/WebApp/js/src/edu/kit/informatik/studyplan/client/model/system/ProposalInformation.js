goog.provide("edu.kit.informatik.studyplan.client.model.system.ProposalInformation");
/**
 * @constructor
 * @extends {Backbone.Model}
 * Model which contains the information need for generating a plan
 * Information:
 *  - number min-semesters: Minimum number of semesters user wants the plan to have
 *  - number max-semesters: Maximum number of semesters user wants the plan to have
 *  - number min-semester-ects: Minimum number of ects the plan should have in each semester
 *  - number max-semester-ects: Maximum number of ects the plan should have in each semester
 *  - FieldCollection fieldCollection: A collection of choosable fields where each field has an id and a curValue
 *  - ObjectiveFunction objectiveFunction: The objective function which should be used
 */

edu.kit.informatik.studyplan.client.model.system.ProposalInformation = Backbone.Model.extend( /** @lends {edu.kit.informatik.studyplan.client.model.system.ProposalInformation.prototype}*/ {});