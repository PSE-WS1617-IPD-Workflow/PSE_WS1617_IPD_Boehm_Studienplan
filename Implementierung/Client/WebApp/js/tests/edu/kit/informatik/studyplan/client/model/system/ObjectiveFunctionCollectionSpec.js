define(["studyplan"], function (client) {
    "use strict";

    var ObjectiveFunctionColletion = client.model.system.ObjectiveFunctionCollection;
    describe("ObjectiveFunctionCollection", function () {
        beforeEach(function () {});
        it("testing basic parsing", function () {
            var json = {
                functions: [{
                    id: 1,
                    name: "Test1",
                    description: "Test1 desc"

                }, {
                    id: 2,
                    name: "Test2",
                    description: "Test2 desc"
                }]
            };
            var objectivecol = new ObjectiveFunctionColletion(json, {
                parse: true
            });
            expect(objectivecol.get(1).id).toEqual(json.functions[0].id);
            expect(objectivecol.get(1).get('name')).toEqual(json.functions[0].name);
            expect(objectivecol.get(1).get('description')).toEqual(json.functions[0].description);
            expect(objectivecol.get(2).id).toEqual(json.functions[1].id);
            expect(objectivecol.get(2).get('name')).toEqual(json.functions[1].name);
            expect(objectivecol.get(2).get('description')).toEqual(json.functions[1].description);
        });
    });
});