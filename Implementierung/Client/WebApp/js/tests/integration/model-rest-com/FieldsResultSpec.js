define(["studyplan"], function (client) {
    "use strict";
    describe("Fields", function () {
        var fieldsCol, resultObject;
        beforeEach(function () {
            jasmine.Ajax.install();
            resultObject = {
                fields: [{
                        id: 1,
                        name: "Field 1",
                        "min-ects": 100
                    },
                    {
                        id: 2,
                        name: "Field 2",
                        "min-ects": 30
                    },
                    {
                        id: 3,
                        name: "Field 3",
                        "min-ects": 10000000000000
                    }
                ]
            };
            fieldsCol = new client.model.system.FieldCollection();
            fieldsCol.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toEqual('http://api.studyplan.devel/fields');
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
                var importantField = fieldsCol.get(i + 1);
                expect(importantField.get('name')).toBeDefined();
                expect(importantField.get('name')).toEqual(resultObject.fields[i].name);
                expect(importantField.get('min-ects')).toEqual(resultObject.fields[i]["min-ects"]);
                expect(importantField.get('id')).toEqual(resultObject.fields[i].id);
            }
        });
        it("Should load single field", function () {
            var field1 = fieldsCol.get(1);
            field1.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toEqual('http://api.studyplan.devel/fields/1');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status": 200,
                "contentType": "application/json",
                "responseText": JSON.stringify({
                    field: {
                        id: 1,
                        name: "Field 1",
                        "min-ects": 100,
                        categories: [{
                            id: 42,
                            name: "Meistern von lebensgefährlichen Situationen"
                        }]
                    }
                })
            });
            expect(field1.get('categories')[0].name).toEqual("Meistern von lebensgefährlichen Situationen");
        });
    });
});