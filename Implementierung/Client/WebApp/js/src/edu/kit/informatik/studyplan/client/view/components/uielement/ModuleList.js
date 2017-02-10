goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/moduleList.html"),
    moduleCollection: null,
    moduleBoxes: null,
    initialize: function(options){
        "use strict";
        //TODO: fetchModuleCollection
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
        this.moduleCollection = new edu.kit.informatik.studyplan.client.model.module.ModuleCollection(json,{parse:true});

        this.moduleBoxes = [];
        var self = this;
        this.moduleCollection.each(function (module) {
            var tmpModuleBox = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox( module );
            self.moduleBoxes.push(tmpModuleBox);
        });
    },
    render: function () {
        this.$el.html(this.template());
        var container = this.$el.find(".profileModuleFinderWrapper");
        var self = this;
        _.each(this.moduleBoxes, function(el){
            self.moduleBoxes.render();
            container.prepend(el.$el)
        })
        this.delegateEvents();
    }
});