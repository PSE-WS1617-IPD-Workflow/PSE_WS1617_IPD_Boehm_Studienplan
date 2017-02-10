goog.provide("edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.module.ModuleCollection}
 */

edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection = edu.kit.informatik.studyplan.client.model.module.ModuleCollection.extend(/** @lends {edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection.prototype}*/{
    url: API_DOMAIN + "/modules",
    
    toSemesterCollection: function(){
        var plan = new edu.kit.informatik.studyplan.client.model.plans.Plan({
                plan    :   {
                    id  :   'someId',
                    name:   'someName',
                    'creditpoints-sum': 0,
                    modules :   [],
                }
            },{parse:true});
        
        var collections = [];
        this.each(function (module) {
            this.plan.addModule(module);
        })    
    }
});
