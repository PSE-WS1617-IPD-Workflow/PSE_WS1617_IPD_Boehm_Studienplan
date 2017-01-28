define(["studyplan"], function (client) {
    "use strict";
    describe("ConstraintInitialisation", function () {
        it("testing parse", function () {

            var json =  {
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
                        constraints: []
                        
                    },
                    second: {
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
                            constraints: []
                        },
                    type: "Himmel und Hölle gleichzeitig zum Ausgleich."
                    };
            var json_copy1 = JSON.parse(JSON.stringify(json));
            var json_copy = json_copy1;
            var constraint1 = new client.model.module.ModuleConstraint(json_copy, {parse : true});
            
            expect(constraint1.get("name")).toEqual(json["name"]);
            expect(constraint1.get("name")).toBeDefined();
            expect(constraint1.get("first").get("name")).toBeDefined();
            expect(constraint1.get("type")).toContain("Hölle");
            expect(constraint1.get("first").get("id")).not.toEqual(constraint1.get("second").get("id"));
            
           //weitere Tests
        });
    
    });
});