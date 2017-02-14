goog.provide("edu.kit.informatik.studyplan.client.model.system.ObjectiveFunction");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 * Object which represents an objective function which my be selcted during the generation wizard
 */

edu.kit.informatik.studyplan.client.model.system.ObjectiveFunction = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.ObjectiveFunction.prototype}*/{
        parse : function (response, options) {
            "use strict";
            return response;
        }
});