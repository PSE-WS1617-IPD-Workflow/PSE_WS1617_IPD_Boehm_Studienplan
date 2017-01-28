define(["studyplan"], function (client) {
    "use strict";
    describe("ModuleResult", function () {
        var planCol, plan, resultObject, planResultObject;
        beforeEach(function () {
            jasmine.Ajax.install();
            resultObject = {
                plans   :   [
                    {
                        id  :   'P1',
                        name:   'Hermines Studienplan',
                        status: 'valid',
                        'creditpoints-sum'  :   9000
                    },
                    {
                        id  :   'P2',
                        name:   'Harrys Studienplan',
                        status: 'not-verified',
                        'creditpoints-sum': 180
                    }, {
                        id  :   'P3',
                        name:   'Rons Studienplan',
                        status: 'invalid',
                        'creditpoints-sum': 100
                    }
                ]
            };
            planResultObject = {
                plan    :   {
                    id  :   'P3',
                    name:   'Rons Studienplan',
                    status: 'invalid',
                    'creditpoints-sum': 100,
                    modules :   [
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
                    ],
                    violations  :   [
                        {
                            name:   "falsch",
                            first:  "M2",
                            second: "M1",
                            type:   "bli bla blub"
                        }
                    ]
                }
            }
            planCol = new client.model.plans.PlanCollection();
            planCol.fetch();
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify(resultObject)
            });
            plan = planCol.get('P3');
        });
        afterEach(function () {
            jasmine.Ajax.uninstall();
        });
        it("/plans/P3", function () {
            plan.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('api.studyplan.devel/plans/P3');
            expect(jasmine.Ajax.requests.mostRecent().method).toBe('GET');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify(planResultObject)
            });
            expect(plan.get('id')).toEqual(planResultObject.plan.id);
            expect(plan.get('name')).toEqual(planResultObject.plan.name);
            expect(plan.get('creditpoints-sum')).toEqual(planResultObject.plan["creditpoints-sum"]);
            expect(plan.get('status')).toEqual(planResultObject.plan.status);
            expect(plan.get('semesterCollection').get(3).get('M1').get('name')).toEqual(planResultObject.plan.modules[0].name);
            expect(plan.get('semesterCollection').get(5).get('M2').get('name')).toEqual(planResultObject.plan.modules[1].name);
        });
    });
});