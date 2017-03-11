goog.provide("edu.kit.informatik.studyplan.client.model.system.Field");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 * represents options that can be chosen by the user to spezialize his generation. 
 * every field represents exact one option 
 */

edu.kit.informatik.studyplan.client.model.system.Field = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend( /** @lends {edu.kit.informatik.studyplan.client.model.system.Field.prototype}*/ {
    /**
     * Key by which error messages are identified
     */
    modelErrorKey: "fields",
    /**
     *just the standard-parse-method
     */
    parse: function (response, options) {
        "use strict";
        //console.log(response);
        return response["field"];
    }
});