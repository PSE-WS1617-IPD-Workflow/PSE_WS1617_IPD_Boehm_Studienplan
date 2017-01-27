define(["studyplan"], function (client) {
    "use strict";
    describe("PreferenceInitialisation", function () {
        it("testing parse", function () {

            var json =  {module:
                         {
                        id : 0,
                        planId: "der perfekte Plan",     
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
                        constraints: [{constraint: {
                            name: "keine Ahnung wozu der gut ist, ich glaube das sollte lieber ID sein, aber dazu bin ich vielleicht nicht befugt.",        
                            first: {module: {
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
                                constraints: []
                            }},
                            second: {module: {
                                //wie stellt man das klugerweise da ? 
                                id : 0
                            }},
                            type: "Himmel und Hölle gleichzeitig zum Ausgleich."

                        }}]
                    
                    }
                        };
            var json_copy = JSON.parse(JSON.stringify(json.module));
            var preference = new client.model.module.Preference(json_copy, {parse : true});
            
            expect(preference.get("value")).toBeDefined();
            expect(preference.get("moduleId")).toBeDefined();
            expect(preference.get("planId")).toBeDefined();
            expect(preference.get("planId")).toEqual("der perfekte Plan");
            expect(preference.get("moduleId")).toEqual(json.module["id"]);
            expect(preference.get("value")).toEqual(json.module["preference"]);
           //weitere Tests
        });
    
    });
});