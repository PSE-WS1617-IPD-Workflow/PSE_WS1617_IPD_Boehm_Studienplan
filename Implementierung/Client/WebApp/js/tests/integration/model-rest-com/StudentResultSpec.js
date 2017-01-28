define(["studyplan"], function (client) {
    "use strict";
    describe("ModuleResult", function () {
        var resultObject, student;
        beforeEach(function () {
            jasmine.Ajax.install();
            resultObject = {
                student :   {
                    discipline: {
                        id: 42
                    },
                    "study-start":  {
                        "semester-type":    "WS",
                        "year": 1825
                    },
                    "passed-modules": [
                        {
                            id          :   "M1",
                            name        :   "Bayrisch",
                            creditpoints:   7,
                            lecturer    :   "Aloisius",
                            preference  :   "positive",
                            semester    :   3
                        },
                        {
                            id          :   "M2",
                            name        :   "Schwäbisch",
                            creditpoints:   5,
                            lecturer    :   "Maultaschius",
                            preference  :   "negative",
                            semester    :   5
                        }
                    ]
                }
            };
            student = new client.model.user.Student({}, {});
            student.fetch();
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify(resultObject)
            });
        });
        afterEach(function () {
            jasmine.Ajax.uninstall();
        });
        it("should contain the right attributes", function () {
            expect(student.get('discipline')).toBe(42);
            expect(student.get('studyStartYear')).toBe(1825);
            expect(student.get("passedModules").get("M1")).toBeDefined();
            expect(student.get("passedModules").get("M1").get("name")).toEqual("Bayrisch");
        });
        it("should save stuff", function () {
            student.get("passedModules").add(new client.model.module.Module({
                module: {
                    id          :   "M5",
                    name        :   "Badisch",
                    creditpoints:   5,
                    lecturer    :   "Badenser",
                    preference  :   "positive",
                    semester    :   5
                }
            }, {parse: true}));
            student.save();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('api.studyplan.devel/student');
            expect(jasmine.Ajax.requests.mostRecent().method).toBe('PUT');
            var data = jasmine.Ajax.requests.mostRecent().data();
            expect(data["student"]["passed-modules"]).toContain({
                    id          :   "M5",
                    semester    :   5
                });
        });
    });
});