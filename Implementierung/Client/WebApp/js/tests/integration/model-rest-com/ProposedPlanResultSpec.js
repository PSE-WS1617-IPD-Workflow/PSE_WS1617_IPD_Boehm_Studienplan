define(["studyplan"], function (client) {
    "use strict";
    describe("Proposed Plan", function () {
        var plan, propPlan, propInfo;
        beforeEach(function () {
            client.config.init();
            jasmine.Ajax.install();
            var resultObject = {
                plan: {
                    id: 'P3',
                    name: 'Harrys Studienplan',
                    status: 'valid',
                    'creditpoints-sum': 170,
                    modules: [{
                            id: "M1",
                            name: "Bayrisch",
                            creditpoints: 7,
                            lecturer: "Aloisius",
                            preference: "positive",
                            semester: 3
                        },
                        {
                            id: "M2",
                            name: "Schwäbisch",
                            creditpoints: 5,
                            lecturer: "Maultaschius",
                            preference: "negative",
                            semester: 5
                        }
                    ],
                    violations: []
                }
            };
            plan = new client.model.plans.Plan({
                id: 'P3'
            });
            plan.fetch();
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status": 200,
                "contentType": "application/json",
                "responseText": JSON.stringify(resultObject)
            });
            propPlan = plan.retrieveProposedPlan();
            propInfo = new client.model.system.ProposalInformation({
                "min-semesters": 1,
                "max-semesters": 9,
                "min-semester-ects": 10,
                "max-semester-ects": 40,
                "objectiveFunction": new client.model.system.ObjectiveFunction({
                    id: 3,
                    name: "abc"
                }),
                "fieldCollection": new client.model.system.FieldCollection([
                    new client.model.system.Field({
                        id: 2,
                        curValue: 7
                    })
                ])
            });
            propPlan.setInfo(propInfo);
        });
        afterEach(function () {
            jasmine.Ajax.uninstall();
        });
        it("GET /plans/P3/proposal/3", function () {
            propPlan.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('http://api.studyplan.devel/plans/P3/proposal/3?min-semesters=1&max-semesters=9&min-semester-ects=10&max-semester-ects=40&field-2=7&fields=2');
            expect(jasmine.Ajax.requests.mostRecent().method).toBe('GET');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status": 200,
                "contentType": "application/json",
                "responseText": JSON.stringify({
                    plan: {
                        status: 'valid',
                        modules: [{
                                id: "M1",
                                name: "Bayrisch",
                                creditpoints: 7,
                                lecturer: "Aloisius",
                                preference: "positive",
                                semester: 3
                            },
                            {
                                id: "M2",
                                name: "Schwäbisch",
                                creditpoints: 5,
                                lecturer: "Maultaschius",
                                preference: "negative",
                                semester: 5
                            },
                            {
                                id: "M3",
                                name: "Badensisch",
                                creditpoints: 5,
                                lecturer: "Badenser",
                                preference: "positive",
                                semester: 5
                            }
                        ],
                        violations: []
                    }
                })
            });
            expect(propPlan.get('semesterCollection').get(5).get("M3").get("name")).toBe("Badensisch");
        });
        it("PUT /plans/P3", function () {
            propPlan.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('http://api.studyplan.devel/plans/P3/proposal/3?min-semesters=1&max-semesters=9&min-semester-ects=10&max-semester-ects=40&field-2=7&fields=2');
            expect(jasmine.Ajax.requests.mostRecent().method).toBe('GET');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status": 200,
                "contentType": "application/json",
                "responseText": JSON.stringify({
                    plan: {
                        status: 'valid',
                        modules: [{
                                id: "M1",
                                name: "Bayrisch",
                                creditpoints: 7,
                                lecturer: "Aloisius",
                                preference: "positive",
                                semester: 3
                            },
                            {
                                id: "M2",
                                name: "Schwäbisch",
                                creditpoints: 5,
                                lecturer: "Maultaschius",
                                preference: "negative",
                                semester: 5
                            },
                            {
                                id: "M3",
                                name: "Badensisch",
                                creditpoints: 5,
                                lecturer: "Badenser",
                                preference: "positive",
                                semester: 5
                            }
                        ],
                        violations: []
                    }
                })
            });
            var realPlan = propPlan.getPlan({
                newPlan: false
            });
            //realPlan.save(null, {patch: false});
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('http://api.studyplan.devel/plans/P3');
            expect(jasmine.Ajax.requests.mostRecent().method).toBe('PUT');
            var data = jasmine.Ajax.requests.mostRecent().data();
            expect(data.plan.modules).toContain({
                id: "M3",
                semester: 5
            });
            expect(realPlan.get('id')).toEqual("P3");
            expect(realPlan.get('semesterCollection').get(5).get("M3").get("name")).toEqual("Badensisch");
        });
        it("PUT /plans/P4", function () {
            propPlan.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('http://api.studyplan.devel/plans/P3/proposal/3?min-semesters=1&max-semesters=9&min-semester-ects=10&max-semester-ects=40&field-2=7&fields=2');
            expect(jasmine.Ajax.requests.mostRecent().method).toBe('GET');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status": 200,
                "contentType": "application/json",
                "responseText": JSON.stringify({
                    plan: {
                        status: 'valid',
                        modules: [{
                                id: "M1",
                                name: "Bayrisch",
                                creditpoints: 7,
                                lecturer: "Aloisius",
                                preference: "positive",
                                semester: 3
                            },
                            {
                                id: "M2",
                                name: "Schwäbisch",
                                creditpoints: 5,
                                lecturer: "Maultaschius",
                                preference: "negative",
                                semester: 5
                            },
                            {
                                id: "M3",
                                name: "Badensisch",
                                creditpoints: 5,
                                lecturer: "Badenser",
                                preference: "positive",
                                semester: 5
                            }
                        ],
                        violations: []
                    }
                })
            });
            var realPlan = propPlan.getPlan({
                newPlan: true,
                planName: "toller, lustiger, intelligenter Name"
            });
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('http://api.studyplan.devel/plans');
            expect(jasmine.Ajax.requests.mostRecent().method).toBe('POST');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status": 200,
                "contentType": "application/json",
                "responseText": JSON.stringify({
                    plan: {
                        id: "P4",
                        name: "toller, lustiger, intelligenter Name"
                    }
                })
            });
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('http://api.studyplan.devel/plans/P4');
            expect(jasmine.Ajax.requests.mostRecent().method).toBe('PUT');
            var data = jasmine.Ajax.requests.mostRecent().data();
            expect(data.plan.modules).toContain({
                id: "M3",
                semester: 5
            });
            expect(realPlan.get('id')).toEqual("P4");
            expect(realPlan.get('semesterCollection').get(5).get("M3").get("name")).toEqual("Badensisch");
            // TODO: testing for passed values
        });
    });
});