/**
 * @Deprecated
define(["studyplan"], function (client) {
    "use strict";
    
    var SubjectDisciplineCollection = client.model.system.SubjectDisciplineCollection;
    describe("SubjectDisciplineCollection", function () {
        beforeEach(function () {
        });
        it("testing basic parsing", function () {
            var json = {
                subjects : [{
                    id : 1,
                    name : "Test1"
                }, {
                    id : 2,
                    name : "Test2"
                }]
            };
            var subjectcol = new SubjectDisciplineCollection(json, {parse : true});
            expect(subjectcol.get(1).id).toEqual(json.subjects[0].id);
            expect(subjectcol.get(1).get('name')).toEqual(json.subjects[0].name);
            expect(subjectcol.get(2).id).toEqual(json.subjects[1].id);
            expect(subjectcol.get(2).get('name')).toEqual(json.subjects[1].name);
        });
    });
});
*/