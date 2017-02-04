define(["studyplan"], function (client) {
    "use strict";
    describe("Objective Function", function () {
        var objectiveCol, resultObject;
        beforeEach(function () {
            jasmine.Ajax.install();
            resultObject = {
                functions: [
                    {
                        id: 1,
                        name: "Funktion 1",
                        description: "Ergreife Weltherrschaft"
                    },
                    {
                        id: 2,
                        name: "Funktion 2",
                        description: "Backe Feta-Käse-Auflauf"
                    },
                    {
                        id: 3,
                        name: "Funktion 3",
                        description: "Rette die Welt"
                    }
                ]
            };
            objectiveCol = new client.model.system.ObjectiveFunctionCollection();
            objectiveCol.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toEqual('http://api.studyplan.devel/objective-functions');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify(resultObject)
            });
        });
        afterEach(function () {
            jasmine.Ajax.uninstall();
        });
        it("Should retrieve data", function () {
            for (var i=0; i<3;i++) {
                var importantFunction = objectiveCol.get(i+1);
                expect(importantFunction.get('name')).toBeDefined();
                expect(importantFunction.get('name')).toEqual(resultObject.functions[i].name);
                expect(importantFunction.get('description')).toEqual(resultObject.functions[i].description);
            }
        });
    });
});