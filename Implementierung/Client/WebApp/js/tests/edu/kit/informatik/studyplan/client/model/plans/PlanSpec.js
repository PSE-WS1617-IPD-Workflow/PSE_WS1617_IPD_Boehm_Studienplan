define(["studyplan"], function (studyplan) {
    "use strict";
    describe("Plan", function () {
        it("testing parse", function () {
            var plan = new studyplan.model.plans.Plan({
                plan: {
                    id: 5,
                    status: "invalid",
                    "creditpoints-sum": 20,
                    name: "test",
                    modules: [{
                            id: 5,
                            name: "testest",
                            semester: 3,
                            creditpoints: 19,
                            lecturer: "Samuel"
                        },
                        {
                            id: 6,
                            name: "testest",
                            semester: 5,
                            creditpoints: 1,
                            lecturer: "Teuber"
                        }
                    ],
                    "violations": [{
                        name: "Blöder Constraint",
                        first: {
                            id: 5
                        },
                        second: {
                            id: 42
                        },
                        type: "abc"
                    }],
                    "rule-group-violations": [{
                        "name": "someNameViolation",
                        "min-ects": 100,
                        "max-ects": 200
                    }],
                    "field-violations": [{
                        id: "42",
                        name: "blaFieldViolation",
                        "min-ects": 10,
                        categories: [{
                            "id": 9001,
                            "name": "over 9000"
                        }]
                    }]
                }
            }, {
                parse: true
            });
            expect(plan.containsModule(5)).toBe(true);
        });
    });
});