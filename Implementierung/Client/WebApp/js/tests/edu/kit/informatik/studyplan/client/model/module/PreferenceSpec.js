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
                        preference: "positive",
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
            var json_copy = JSON.parse(JSON.stringify(json));
            var preference = new client.model.module.Module(json_copy, {parse : true});
            
            expect(preference.get('preference').get("preference")).toBeDefined();
            expect(preference.get('preference').get("module")).toBeDefined();
            //expect(preference.get("planId")).toBeDefined();
            //expect(preference.get("planId")).toEqual("der perfekte Plan");
            //expect(preference.get("module")).toEqual(json.module["id"]);
            expect(preference.get('preference').get("preference")).toEqual(json.module["preference"]);
           //weitere Tests
        });
    
    });
});