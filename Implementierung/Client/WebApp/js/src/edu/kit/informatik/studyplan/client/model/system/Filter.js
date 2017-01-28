goog.provide("edu.kit.informatik.studyplan.client.model.system.Filter");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 */

edu.kit.informatik.studyplan.client.model.system.Filter = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.Filter.prototype}*/{
    /*
     * Information concerning the curValue of filters:
     * For range filters it is set by an object of the form {min: <min val>, max: <max val>}
     * For list filters it is set by an object of the form {id: <el id>, text: <el text>}
     * For contains filter is set by a string
     */
    parse : function (response, options) {
        "use strict";
        response["curValue"] = response["default-value"];
        return response;
    },
    filterTypeHandler : {
        /**
         * @this {edu.kit.informatik.studyplan.client.model.system.Filter}
         */
        "range" : function () {
            "use strict";
            var result = {};
            result[this.get('name') + "-min"] = this.get('curValue').min;
            result[this.get('name') + "-max"] = this.get('curValue').max;
            return result;
        },
        /**
         * @this {edu.kit.informatik.studyplan.client.model.system.Filter}
         */
        "list" : function () {
            "use strict";
            var result = {};
            result[this.get('name')] = this.get('curValue');
            return result;
        },
        /**
         * @this {edu.kit.informatik.studyplan.client.model.system.Filter}
         */
        "contains" : function () {
            "use strict";
            var result = {};
            result[this.get('name')] = this.get('curValue');
            return result;
        }
    },
    getParams : function () {
        "use strict";
        return this.filterTypeHandler[this.get('specification').type].apply(this);
    }
});