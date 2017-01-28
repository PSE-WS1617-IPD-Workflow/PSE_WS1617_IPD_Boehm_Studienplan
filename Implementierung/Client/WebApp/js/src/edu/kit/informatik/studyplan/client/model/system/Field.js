goog.provide("edu.kit.informatik.studyplan.client.model.system.Field");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 */

edu.kit.informatik.studyplan.client.model.system.Field = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.Field.prototype}*/{
    parse : function (response, options) {
        "use strict";
        //console.log(response);
        return response["field"];
    }
});