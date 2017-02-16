goog.provide("edu.kit.informatik.studyplan.client.model.user.Student");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 * Class which represents a student
 */

edu.kit.informatik.studyplan.client.model.user.Student = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend( /** @lends {edu.kit.informatik.studyplan.client.model.user.Student.prototype}*/ {
    url: API_DOMAIN + "/student",
    /**
     *
     */
    parse: function (response, options) {
        "use strict";
        response = response["student"];
        if (response !== undefined) {
            if (response["study-start"]) {
                response["studyStartYear"] = response["study-start"]["year"];
                response["studyStartCycle"] = response["study-start"]["semester-type"];
            } else {
                response["studyStartYear"] = null;
                response["studyStartCycle"] = null;
            }
            if (response["discipline"]) {
                response["discipline"] = response["discipline"]["id"];
            }
            if (response["passed-modules"]) {
                response["passedModules"] = new edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection({
                    modules: response["passed-modules"]
                }, {
                    parse: true
                });
            } else {
                response["passedModules"] = new edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection({
                    modules: []
                }, {
                    parse: true
                });
            }
            //Name und Id fehlen noch
            //was ist mit discipline Id?
        }
        return response;
    },
    toJSON: function (options) {
        "use strict";
        var result = {};
        result["discipline"] = {
            id: this.get('discipline')
        };
        result["study-start"] = {
            "semester-type": this.get("studyStartCycle"),
            "year": this.get("studyStartYear")
        };
        result["passed-modules"] = this.get("passedModules").toJSON(options)["modules"];
        return {
            student: result
        };
    },
    isNew: function () {
        return false;
    }



});