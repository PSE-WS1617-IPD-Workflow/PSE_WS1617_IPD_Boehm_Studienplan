define(["studyplan"], function (client) {
    "use strict";
    describe("Plans", function () {
        var planCol, resultObject;
        beforeEach(function () {
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
            planCol = new client.model.plans.PlanCollection();
            jasmine.Ajax.install();
        });
        afterEach(function () {
            jasmine.Ajax.uninstall();
        });
        it("/plans", function () {
            planCol.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('api.studyplan.devel/plans');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify(resultObject)
            });
            expect(planCol.get('P1').get('id')).toEqual(resultObject.plans[0].id);
            expect(planCol.get('P1').get('name')).toEqual(resultObject.plans[0].name);
            expect(planCol.get('P1').get('status')).toEqual(resultObject.plans[0].status);
            expect(planCol.get('P1').get('creditpoints-sum')).toEqual(resultObject.plans[0]["creditpoints-sum"]);
            
            expect(planCol.get('P2').get('id')).toEqual(resultObject.plans[1].id);
            expect(planCol.get('P2').get('name')).toEqual(resultObject.plans[1].name);
            expect(planCol.get('P2').get('status')).toEqual(resultObject.plans[1].status);
            expect(planCol.get('P2').get('creditpoints-sum')).toEqual(resultObject.plans[1]["creditpoints-sum"]);
            
            expect(planCol.get('P3').get('id')).toEqual(resultObject.plans[2].id);
            expect(planCol.get('P3').get('name')).toEqual(resultObject.plans[2].name);
            expect(planCol.get('P3').get('status')).toEqual(resultObject.plans[2].status);
            expect(planCol.get('P3').get('creditpoints-sum')).toEqual(resultObject.plans[2]["creditpoints-sum"]);
            
        });
        it("POST /plans", function () {
            var plan = planCol.add(new client.model.plans.Plan({
                name    :   "Nevils Studienplan"
            }));
            plan.save();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('api.studyplan.devel/plans');
            expect(jasmine.Ajax.requests.mostRecent().method).toBe('POST');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify({
                    plan    :   {id  :   "P4",   name    :   "Nevils Studienplan"}
                })
            });
            expect(plan.get('id')).toEqual('P4');
        });
    });
});