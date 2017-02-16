define(["studyplan"], function (client) {
    "use strict";
    describe("ModuleICollectionInitialisierung", function () {
        it("testing parse", function () {

            var json = {
                modules: [{
                        id: 0,
                        name: "Magische Tierwesen",
                        categories: //test,
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
                                id: 1
                            },
                            second: {
                                //wie stellt man das klugerweise da ? 
                                id: 0
                            },
                            type: "Himmel und Hölle gleichzeitig zum Ausgleich."

                        }]
                    },
                    {
                        id: 1,
                        name: "Zaubertränke",
                        categories: [{
                            id: 13,
                            name: "Mord und Heilung"
                        }],
                        semester: 5,
                        "cycle-type": "Mittsommer",
                        lecturer: "Snape",
                        preference: "0",
                        description: "Flüssiges Glück und dampfender Tot verkorkt. Unter Aufsicht eines epischen Tyrannen.",
                        constraints: []
                    }
                ]
            };
            var json_copy = JSON.parse(JSON.stringify(json));
            var module = new client.model.module.ModuleCollection(json_copy, {
                parse: true
            });
            expect(module.get(0).get("name")).toEqual(json.modules[0]["name"]);
            expect(module.get(1).get("name")).toContain("Zaubertränke");
            expect(module.get(2)).not.toBeDefined();
            expect(module.get(0).get("constraints")).toBeDefined();
            expect(module.containsModule(0)).toBe(true);


            //weitere Tests: 
        });

    });
});