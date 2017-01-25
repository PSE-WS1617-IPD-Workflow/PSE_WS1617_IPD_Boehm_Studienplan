define(["studyplan"], function (client) {
    "use strict";
    describe("ModuleInitialisation", function () {
        it("testing parse", function () {

            var json =  {
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
                            },
                            second: {
                                //wie stellt man das klugerweise da ? 
                                id : 0
                            },
                            type: "Himmel und Hölle gleichzeitig zum Ausgleich."

                        }]
                    
                };
            var json_copy = JSON.parse(JSON.stringify(json));
            var module = new client.model.module.Module(json_copy, {parse : true});
            
            expect(module.get("name")).toEqual(json["name"]);
            expect(module.get("lecturer")).toEqual(json["lecturer"]);
            expect(module.get("preference").get("value")).toEqual(json["preference"]);
            expect(module.get("description")).toEqual(json["description"]);
            expect(module.get("cycle-type")).toEqual(json["cycle-type"]);
            expect(module.get("semester")).toEqual(json["semester"]);
            expect(module.get("id")).toEqual(json["id"]);
            expect(module.get("categories")[0]).toEqual(json.categories[0]["name"]);
            expect(module.get("constraints")[0].get("name")).toEqual(json.constraints[0].name);
            expect(module.get("constraints")[0].get("first").get("name")).toEqual(json.constraints[0].first.name);
            expect(module.get("constraints")[0].get("second").get("name")).toEqual(json.constraints[0].second.name);
            expect(module.get("constraints")[0].get("type")).toEqual(json.constraints[0].type);
            expect(module.get("constraints")[0].get("first").get("description")).toEqual(json.constraints[0].first["description"]);
           //weitere Tests: Array nicht mehr Werte als vorgesehen, PlanID, passed, discipline, preference ausführlicher, leere Felder, tiefere Constraintverknüpfungen.
        });
    
    });
});