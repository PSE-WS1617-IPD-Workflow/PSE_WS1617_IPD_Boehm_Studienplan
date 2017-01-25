define(["studyplan"], function (client) {
    "use strict";
    
    var DisciplineCollection = client.model.system.DisciplineCollection;
    describe("DisciplineCollection", function () {
        beforeEach(function () {
        });
        it("testing basic parsing", function () {
            var json = {
                disciplines : [{
                    id : 1,
                    name : "Test1"
                }, {
                    id : 2,
                    name : "Test2"
                }]
            };
            var disciplinecol = new DisciplineCollection(json, {parse : true});
            expect(disciplinecol.get(1).id).toEqual(json.disciplines[0].id);
            expect(disciplinecol.get(1).get('name')).toEqual(json.disciplines[0].name);
            expect(disciplinecol.get(2).id).toEqual(json.disciplines[1].id);
            expect(disciplinecol.get(2).get('name')).toEqual(json.disciplines[1].name);
            
            disciplinecol.get(1).set("name", "blub");
            expect(json.disciplines[0].name).toEqual("Test1");
            expect( disciplinecol.get(1).get("name")).toEqual("blub");
        });
    });
});