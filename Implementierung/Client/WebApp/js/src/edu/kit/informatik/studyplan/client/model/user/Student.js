goog.provide("edu.kit.informatik.studyplan.client.model.user.Student");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 */

edu.kit.informatik.studyplan.client.model.user.Student = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.user.Student.prototype}*/{
    url: API_DOMAIN + "/student",
    /**
    *
    */
    parse : function (response, options) {
        "use strict";
        response = response["student"];
        if (response !== undefined) {
            response["studyStartYear"] = response["study-start"]["year"];
            response["studyStartCycle"] = response["study-start"]["semester-type"];
            response["discipline"] = response["discipline"]["id"];
            response["passedModules"] = new edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection({modules: response["passed-modules"]}, {parse: true});
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
        return {student: result};
    },
    isNew: function () {
        return false;
    }
    
        
        
});