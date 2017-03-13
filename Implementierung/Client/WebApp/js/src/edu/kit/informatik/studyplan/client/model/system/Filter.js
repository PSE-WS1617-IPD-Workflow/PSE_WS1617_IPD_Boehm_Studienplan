goog.provide("edu.kit.informatik.studyplan.client.model.system.Filter");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 * This class represents a filter which might be activated and used for filtering modules
 */

edu.kit.informatik.studyplan.client.model.system.Filter = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend( /** @lends {edu.kit.informatik.studyplan.client.model.system.Filter.prototype}*/ {
    /**
     * Key by which error messages are identified
     */
    modelErrorKey: "filters",
    /*
     * Information concerning the curValue of filters:
     * For range filters it is set by an object of the form {min: <min val>, max: <max val>}
     * For list filters it is set by an object of the form {id: <el id>, text: <el text>}
     * For contains filter is set by a string
     */
    parse: function (response, options) {
        "use strict";
        response["curValue"] = JSON.parse(JSON.stringify(response["default-value"]));
        if (response["specification"]["type"] === "list") {
            response["curValue"] = "none";
            response["default-value"] = "none";
        }
        return response;
    },
    /**
     * This object contains query parameter builders for various filter types
     */
    filterTypeHandler: {
        /**
         * @this {edu.kit.informatik.studyplan.client.model.system.Filter}
         */
        "range": function () {
            "use strict";
            var result = {};
            result[this.get('uri-name') + "-min"] = this.get('curValue').min;
            result[this.get('uri-name') + "-max"] = this.get('curValue').max;
            return result;
        },
        /**
         * @this {edu.kit.informatik.studyplan.client.model.system.Filter}
         */
        "list": function () {
            "use strict";
            var result = {};
            result[this.get('uri-name')] = this.get('curValue');
            return result;
        },
        /**
         * @this {edu.kit.informatik.studyplan.client.model.system.Filter}
         */
        "contains": function () {
            "use strict";
            var result = {};
            result[this.get('uri-name')] = this.get('curValue');
            return result;
        }
    },
    /**
     * Method for retrieving query parameters of a filter
     * @return {Object|null} containing the values of the query 
     */
    getParams: function () {
        "use strict";
        if (this.get('curValue') != "none") {
            return this.filterTypeHandler[this.get('specification').type].apply(this);
        } else {
            return null;
        }
    }
});
