goog.provide("edu.kit.informatik.studyplan.client.view.subview.ProfilPage");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.ProfilPage = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.ProfilPage.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/subview/profilPage.html"),
    
    moduleFinder: null,
    planView: null,
    passedModules: null,
    events: {
      "click button.mainPageNavigation": "navigateToMainPage"  
    },    
    initialize: function (){
        //TODO: isPlaced
        this.moduleFinder = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder({
            isDraggable: true,
            isPreferencable: false
        });
        
        
        
        //Initialize plan view
        //TODO: replace with fetch
        var json = {
            modules: [
                    {
                        id : 0,
                        name : "Magische Tierwesen",
                        categories://test,
                            [{
                                id: 42,
                                name: "Meistern von lebensgefährliche n Situationen"
                            }],
                        semester: 5,
                        "cycle-type": "Mittsommer",
                        lecturer: "Hagrid",
                        preference: 1,
                        description: "Auf Heippogreifen reiten, Schrumpfhörnige Schnarchkackler füttern und beißende Bücher bändigen. Spannung Spaß und Abenteuer im Verbotenen Wald.",
                        constraints: [{
                            name: "keine Ahnung wozu der gut ist, ich glaube das sollte lieber ID sein, aber dazu bin ich vielleicht nicht befugt.",
        
                            first: {
                                id : 1
                            },
                            second: {
                                //wie stellt man das klugerweise da ? 
                                id : 0
                            },
                            type: "Himmel und Hölle gleichzeitig zum Ausgleich."

                        }]
                    },
                    {
                        id : 1,
                        name : "Zaubertränke",
                        categories:
                                [{
                                id: 13,
                                name: "Mord und Heilung"
                            }],
                        semester: 5,
                        "cycle-type": "Mittsommer",
                        lecturer: "Snape",
                        preference: "0",
                        description: "Flüssiges Glück und dampfender Tot verkorkt. Unter Aufsicht eines epischen Tyrannen.",
                        constraints: [
                        ]
                    }
                ]
        };
        this.passedModules = new edu.kit.informatik.studyplan.client.model.user.PassedModuleCollection(json, {parse:true});
        //END_REPLACE
        
        
        //TODO: fix this
        console.log("[edu.kit.informatik.studyplan.client.view.subview.ProfilPage]");
        console.log(this.passedModules);
        this.planView = new edu.kit.informatik.studyplan.client.view.components.uielement.Plan({
            plan: this.passedModules.toPlan()
        });

        console.log(this.passedModules.toPlan());
        
    },
    
    /**
    *
    */
    close: function () {
        "use strict";
    },
    /**
    *@param {edu.kit.informatik.studyplan.client.model.module.Module} module
    */
    showModuleDetails:
        function (module) {
            "use strict";
    },
    /**
    * @this {Backbone.View}
    * @return *    
    */
    render: function () {
        "use strict";
        this.$el.html(this.template());
        var finder = this.$el.find(".profileEditModuleFinderWrapper");
        
        this.moduleFinder.render();
        finder.append(this.moduleFinder.$el);
        
        
        var profile = this.$el.find("profileEditWrapper");
        this.planView.render();
        finder.append(this.planView.$el);
        
        
        this.delegateEvents();
    },
    /**
    *
    */
    hideModuleDetails: function () {
            "use strict";
    },
    save(){
      //TODO: get passedModules from plan
        //this.passedModules = ....fromPlan(planView.getPlan());
    },
    navigateToMainPage: function () {
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().navigate("/",{trigger:true});
    }
});
