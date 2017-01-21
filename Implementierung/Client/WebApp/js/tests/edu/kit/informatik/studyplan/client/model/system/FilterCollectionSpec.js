define(["studyplan"], function (client) {
    "use strict";
    
    var FilterCollection = client.model.system.FilterCollection;
    describe("FilterCollection", function () {
        beforeEach(function () {
        });
        it("testing basic parsing", function () {
            var json = {
                filters : [{
                    id : 1,
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
                }]
            };
            var filtercol = new FilterCollection(json, {parse : true});
            console.log(filtercol);
            console.log(filtercol.get(0));
            
            expect(filtercol.get(0).id).toEqual(1);
            expect(filtercol.get(0).get('name')).toEqual("Test");
            expect(filtercol.get(0).get('default-value').min).toEqual(10);
            expect(filtercol.get(0).get('default-value').max).toEqual(20);
            expect(filtercol.get(0).get('tooltip')).toEqual("Test");
            expect(filtercol.get(0).get('specification').type).toEqual("range");
            expect(filtercol.get(0).get('specification').min).toEqual(0);
            expect(filtercol.get(0).get('specification').max).toEqual(200);
            
        });
    });
});