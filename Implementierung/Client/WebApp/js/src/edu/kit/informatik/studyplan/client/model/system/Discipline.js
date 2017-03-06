goog.provide("edu.kit.informatik.studyplan.client.model.system.Discipline");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 * Represents a discipline a student can select for building his plans
 */

edu.kit.informatik.studyplan.client.model.system.Discipline = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend( /** @lends {edu.kit.informatik.studyplan.client.model.system.Discipline.prototype}*/ {
    parse: function (response, options) {
        "use strict";
        return response;
    }
});