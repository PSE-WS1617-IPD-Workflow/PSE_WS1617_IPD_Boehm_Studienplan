goog.provide("edu.kit.informatik.studyplan.client.model.system.FieldCollection");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} optionas
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection}
 */

edu.kit.informatik.studyplan.client.model.system.FieldCollection = edu.kit.informatik.studyplan.client.model.system.OAuthCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.system.FieldCollection.prototype}*/{
    url: API_DOMAIN + "/fields",
    model : edu.kit.informatik.studyplan.client.model.system.Field,
    parse : function (response, options) {
        var fields = [];
        for(var i = 0; i<response["fields"].length; i++){
            fields.push(new this.model({field:response["fields"][i]},{parse : true}));
        }
        //console.log(sub);
        return fields;
    }
});