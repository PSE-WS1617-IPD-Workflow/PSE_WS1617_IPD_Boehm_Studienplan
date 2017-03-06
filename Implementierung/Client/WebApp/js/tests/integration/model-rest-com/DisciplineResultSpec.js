define(["studyplan"], function (client) {
    "use strict";
    describe("Discipline", function () {
        var disciplineCol, resultObject;
        beforeEach(function () {
            jasmine.Ajax.install();
            resultObject = {
                disciplines: [{
                        id: 1,
                        name: "Irgendwas mit Medien",
                    },
                    {
                        id: 2,
                        name: "Irgendwas mit Menschen"
                    },
                    {
                        id: 3,
                        name: "Irgendwas mit Tieren"
                    }
                ]
            };
            disciplineCol = new client.model.system.DisciplineCollection();
            disciplineCol.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toEqual('http://api.studyplan.devel/disciplines');
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
                var importantDiscipline = disciplineCol.get(i + 1);
                expect(importantDiscipline.get('id')).toBeDefined();
                expect(importantDiscipline.get('name')).toEqual(resultObject.disciplines[i].name);
                expect(importantDiscipline.get('id')).toEqual(resultObject.disciplines[i].id);
            }
        });
    });
});