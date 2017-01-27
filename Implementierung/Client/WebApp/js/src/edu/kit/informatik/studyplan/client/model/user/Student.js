goog.provide("edu.kit.informatik.studyplan.client.model.user.Student");
/**
 * @constructor
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 */

edu.kit.informatik.studyplan.client.model.user.Student = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.user.Student.prototype}*/{
    
    /**
    *
    */
    parse : function (response, options) {
        "use strict";
        response = response["student"];
        if(response !== undefined){
            response["studyStartYear"] = response["study-start"]["year"];
            response["studyStartCycle"] = response["study-start"]["semester-type"];
            response["discipline"] = response ["discipline"]["name"];
            //Name und Id fehlen noch
            //was ist mit discipline Id?
        }
    }
    
        
        
});