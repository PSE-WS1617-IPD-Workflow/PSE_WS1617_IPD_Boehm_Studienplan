goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleFinder.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/moduleFinder.html"),
    className: "moduleFinderWrapper",
    /**  @type {edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter} */
    moduleFilter: null,
    /**  @type {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList} */
    moduleList: null,
    events: {
        "change" : "",
        "click .filterButton": "refreshSearchCollection"
    },
    /**
    * parameters:
    *   isDraggable -> modules are draggable
    *   isPreferencable -> preference for modules can be set
    *
    * @this {Backbone.View}
    * @param {...*} options
    * @return *
    */
    initialize: function (options) {
        "use strict";
        this.planId = options.planId;
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
                        creditpoints: 18,
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
                        creditpoints:700,
                        "cycle-type": "Mittsommer",
                        lecturer: "Snape",
                        preference: "0",
                        description: "Flüssiges Glück und dampfender Tot verkorkt. Unter Aufsicht eines epischen Tyrannen.",
                        constraints: [
                        ]
                    },
                    {
                        id : 2,
                        name : "Zaubertränke 2",
                        categories:
                                [{
                                id: 13,
                                name: "Mord und Heilung"
                            }],
                        semester: 5,
                        creditpoints:700,
                        "cycle-type": "Mittsommer",
                        lecturer: "Snape",
                        preference: "0",
                        description: "Flüssiges Glück und dampfender Tot verkorkt. Unter Aufsicht eines epischen Tyrannen.",
                        constraints: [
                        ]
                    },
                    {
                        id : 3,
                        name : "Zaubertränke 3",
                        categories:
                                [{
                                id: 13,
                                name: "Mord und Heilung"
                            }],
                        semester: 5,
                        creditpoints:700,
                        "cycle-type": "Mittsommer",
                        lecturer: "Snape",
                        preference: "0",
                        description: "Flüssiges Glück und dampfender Tot verkorkt. Unter Aufsicht eines epischen Tyrannen.",
                        constraints: [
                        ]
                    },
                    {
                        id : 4,
                        name : "Zaubertränke 4",
                        categories:
                                [{
                                id: 13,
                                name: "Mord und Heilung"
                            }],
                        semester: 5,
                        creditpoints:700,
                        "cycle-type": "Mittsommer",
                        lecturer: "Snape",
                        preference: "0",
                        description: "Flüssiges Glück und dampfender Tot verkorkt. Unter Aufsicht eines epischen Tyrannen.",
                        constraints: [
                        ]
                    }
                ]
            };
        this.moduleCollection = new edu.kit.informatik.studyplan.client.model.system.SearchCollection(json,{
            parse:true,
            planId: this.planId
        });
        this.filterCollection = new edu.kit.informatik.studyplan.client.model.system.FilterCollection(   {                  filters : [{
                    id : 0,
                    name : "Test",
                    'default-value': {
                        min : 10,
                        max : 20
                    },
                    tooltip : "Test",
                    specification: {
                        type : "range",
                        min : 0,
                        max : 200
                    }
                }, {
                    id : 4,
                    name : "TestList",
                    'default-value': 1,
                    tooltip : "Test",
                    specification: {
                        type : "list",
                        items : [{
                            id: 1,
                            text:"a"
                        },{
                            id: 2,
                            text:"b"
                        }, {
                            id: 3,
                            text:"c"
                        }]
                    }
                },{
                    id : 2,
                    name : "TestContains",
                    'default-value': "testDefVal",
                    tooltip : "Test",
                    specification: {
                        type : "contains",
                    }
                }]}, {parse:true});
        this.moduleCollection.setFilters(this.filterCollection);
        this.moduleList = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleList( {               
                isRemovable: false,
                isDraggable: options.isDraggable,
                isPreferencable: options.isPreferencable,
                planId: this.planId,
                moduleCollection: this.moduleCollection
        });
        this.moduleFilter = new edu.kit.informatik.studyplan.client.view.components.filter.ModuleFilter( {
            filterCollection: this.filterCollection
        } );       
        
    },
    /**
    * Renders the ModuleFinder whereby 
    *   uiFilters will be put into .profileModuleFinderWrapper
    *   uiModuleCollection will be put into .profileModuleCollectionWrapper
    * @this {Backbone.View}
    * @return *    
    */
    render: function () {
        "use strict";
        this.$el.html(this.template());
        
        var filter = this.$el.find(".profileModuleFinderWrapper");
        this.moduleFilter.render();
        filter.prepend(this.moduleFilter.$el);
        
        var list = this.$el.find(".profileModuleCollectionWrapper");
        this.moduleList.render();
        list.prepend(this.moduleList.$el);
        
        this.delegateEvents();
    },
    /**
    * Reloads modules with applied filters
    */
    refreshSearchCollection: function () {
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        this.moduleCollection.fetch();
    }
});
