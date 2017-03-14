define(["studyplan"], function (client) {
    "use strict";
    describe("Filter", function () {
        var filterCol, resultObject;
        beforeEach(function () {
            jasmine.Ajax.install();
            resultObject = {
                filters: [{
                        id: 1,
                        name: "Filter 1",
                        "default-value": {
                            min: 5,
                            max: 10
                        },
                        tooltip: "The cake is a lie!",
                        specification: {
                            type: "range",
                            min: 5,
                            max: 1825
                        }
                    },
                    {
                        id: 2,
                        name: "Filter 2",
                        "default-value": 2,
                        tooltip: "Ich bin ein super Filter, viel besser als der Filter 1",
                        specification: {
                            type: "list",
                            items: [{
                                    id: 2,
                                    text: "Mayo"
                                },
                                {
                                    id: 42,
                                    text: "Ketchup"
                                }
                            ]
                        }
                    },
                    {
                        id: 3,
                        name: "Filter 3",
                        "default-value": "Sonderbar",
                        tooltip: "Ich bin ein ganz toller Filter, viel besser als alle anderen (insbesondere besser als Filter 1 und Filter 2)",
                        specification: {
                            type: "contains"
                        }
                    }
                ]
            };
            filterCol = new client.model.system.FilterCollection();
            filterCol.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toEqual('http://api.studyplan.devel/filters');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status": 200,
                "contentType": "application/json",
                "responseText": JSON.stringify(resultObject)
            });
        });
        afterEach(function () {
            jasmine.Ajax.uninstall();
        });
        it("Should retrieve data", function () {
            for (var i = 0; i < 3; i++) {
                var importantFilter = filterCol.get(i + 1);
                expect(importantFilter.get('id')).toBeDefined();
                expect(importantFilter.get('id')).toEqual(resultObject.filters[i].id);
                expect(importantFilter.get('name')).toEqual(resultObject.filters[i].name);
                if(resultObject.filters[i]["specification"]["type"]=="list"){
                    expect(importantFilter.get('default-value')).toEqual("none");
                }else{
                    expect(importantFilter.get('default-value')).toEqual(resultObject.filters[i]["default-value"]);
                }
                expect(importantFilter.get('tooltip')).toEqual(resultObject.filters[i].tooltip);

            }
        });
    });
});