define(["studyplan"], function (client) {
    "use strict";
    describe("ModuleInitialisation", function () {
        it("testing parse", function () {

            var json =  {module:
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
        
                            first: {module:{
                                id : 1,
                                name : "Zaubertränke",
                                categories:
                                        {
                                        id: 13,
                                        name: "Mord und Heilung"
                                    },
                                semester: 5,
                                "cycle-type": "Mittsommer",
                                lecturer: "Snape",
                                preference: "0",
                                description: "Flüssiges Glück und dampfender Tot verkorkt. Unter Aufsicht eines epischen Tyrannen.",
                                constraints: {
                                }
                            }},
                            second: {module:{
                                //wie stellt man das klugerweise da ? 
                                id : 0
                            }},
                            type: "Himmel und Hölle gleichzeitig zum Ausgleich."

                        }]
                    
                    }
                        };
            var json_copy1 = JSON.parse(JSON.stringify(json.module));
            var json_copy = {module: json_copy1};
            var module1 = new client.model.module.Module(json_copy, {parse : true});
            
            expect(module1.get("name")).toEqual(json.module["name"]);
            //expect(module1.get("name")).not.toBeDefined();
            expect(module1.get("lecturer")).toEqual(json.module["lecturer"]);
            expect(module1.get("preference").get("value")).toEqual(json.module["preference"]);
            expect(module1.get("description")).toEqual(json.module["description"]);
            expect(module1.get("cycle-type")).toEqual(json.module["cycle-type"]);
            expect(module1.get("semester")).toEqual(json.module["semester"]);
            expect(module1.get("id")).toEqual(json.module["id"]);
            expect(module1.get("categories")[0]).toEqual(json.module.categories[0]["name"]);
            expect(module1.get("constraints")[0].get("name")).toEqual(json.module.constraints[0].name);
            expect(module1.get("constraints")[0].get("first").get("name")).toEqual(json.module.constraints[0].first.module.name);
            expect(module1.get("constraints")[0].get("second").get("name")).toEqual(json.module.constraints[0].second.name);
            expect(module1.get("constraints")[0].get("type")).toEqual(json.module.constraints[0].type);
            expect(module1.get("constraints")[0].get("first").get("description")).toEqual(json.module.constraints[0].first.module["description"]);
           //weitere Tests: Array nicht mehr Werte als vorgesehen, PlanID, passed, discipline, preference ausführlicher, leere Felder, tiefere Constraintverknüpfungen.
        });
    
    });
});