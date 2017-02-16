define(["studyplan"], function (client) {
    "use strict";

    var FilterCollection = client.model.system.FilterCollection;
    describe("FilterCollection", function () {
        beforeEach(function () {});
        it("testing basic parsing", function () {
            var json = {
                filters: [{
                    id: 0,
                    name: "Test",
                    'default-value': {
                        min: 10,
                        max: 20
                    },
                    tooltip: "Test",
                    specification: {
                        type: "range",
                        min: 0,
                        max: 200
                    }
                }]
            };
            var filtercol = new FilterCollection(json, {
                parse: true
            });

            expect(filtercol.get(0).id).toEqual(json.filters[0].id);
            expect(filtercol.get(0).get('name')).toEqual(json.filters[0].name);
            expect(filtercol.get(0).get('default-value').min).toEqual(json.filters[0]["default-value"].min);
            expect(filtercol.get(0).get('default-value').max).toEqual(json.filters[0]["default-value"].max);
            expect(filtercol.get(0).get('tooltip')).toEqual(json.filters[0].tooltip);
            expect(filtercol.get(0).get('specification').type).toEqual(json.filters[0].specification.type);
            expect(filtercol.get(0).get('specification').min).toEqual(json.filters[0].specification.min);
            expect(filtercol.get(0).get('specification').max).toEqual(json.filters[0].specification.max);

        });
    });
});