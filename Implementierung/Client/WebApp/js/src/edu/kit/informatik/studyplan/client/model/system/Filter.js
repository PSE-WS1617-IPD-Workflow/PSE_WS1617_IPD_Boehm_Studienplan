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
        if(response["specification"]["type"]==="list"){
            response["curValue"] = "none";
        }
        return response;
    },
    filterTypeHandler : {
        /**
         * @this {edu.kit.informatik.studyplan.client.model.system.Filter}
         */
        "range" : function () {
            "use strict";
            var result = {};
            result[this.get('uri-name') + "-min"] = this.get('curValue').min;
            result[this.get('uri-name') + "-max"] = this.get('curValue').max;
            return result;
        },
        /**
         * @this {edu.kit.informatik.studyplan.client.model.system.Filter}
         */
        "list" : function () {
            "use strict";
            var result = {};
            result[this.get('uri-name')] = this.get('curValue');
            return result;
        },
        /**
         * @this {edu.kit.informatik.studyplan.client.model.system.Filter}
         */
        "contains" : function () {
            "use strict";
            var result = {};
            result[this.get('uri-name')] = this.get('curValue');
            return result;
        }
    },
    getParams : function () {
        "use strict";
        if(this.get('curValue')!="none"){
            return this.filterTypeHandler[this.get('specification').type].apply(this);
        } else {
            return null;
        }
    }
});