define(["studyplan"], function (client) {
    "use strict";
    var searchCol, filterCol, resultObject;
    beforeEach(function () {
        searchCol = new client.model.system.SearchCollection({}, {planId : "abcdef"});
        filterCol = new client.model.system.FilterCollection({
            filters: [
                {
                    "name"  :   "testFilter1",
                    "default-value" : {min: 0, max: 20},
                    "tooltip"  :    "Test Range Filter",
                    "specification" :   {
                        "type"  :   "range",
                        "min"   :   0,
                        "max"   :   15
                    }
                },
                {
                    "name"  :   "testFilter2",
                    "default-value" : "abc",
                    "tooltip"  :    "Test List Filter",
                    "specification" :   {
                        "type"  :   "contains"
                    }
                },
                {
                    "name"  :   "testFilter3",
                    "default-value" : {id : 1, text : "test1"},
                    "tooltip"  :    "Test List Filter",
                    "specification" :   {
                        "type"  :   "list",
                        "items" :   [
                            {
                                "id"    :   1,
                                "text"  :   "test1"
                            },
                            {
                                "id"    :   2,
                                "text"  :   "test2"
                            }
                        ]
                    }
                }
            ]
        }, {parse : true});
        resultObject = {
            modules : [
                {
                    id          :   "M1",
                    name        :   "Bayrisch",
                    creditpoints:   7,
                    lecturer    :   "Aloisius"
                },
                {
                    id          :   "M2",
                    name        :   "Schwäbisch",
                    creditpoints:   5,
                    lecturer    :   "Maultaschius"
                }
            ]
        };
        searchCol.setFilters(filterCol);
    });
    describe("ModuleResult", function () {
        beforeEach(function () {
            jasmine.Ajax.install();
        });
        afterEach(function () {
            jasmine.Ajax.uninstall();
        });
        it("/plan/abcdef/modules", function () {
            searchCol.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('api.studyplan.devel/plans/abcdef/modules?filter=testFilter1%2CtestFilter2%2CtestFilter3&testFilter1-min=0&testFilter1-max=20&testFilter2=abc&testFilter3=1');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify(resultObject)
            });
            expect(searchCol.get('M1').get('id')).toEqual(resultObject.modules[0].id);
            expect(searchCol.get('M1').get('name')).toEqual(resultObject.modules[0].name);
            expect(searchCol.get('M1').get('creditpoints')).toEqual(resultObject.modules[0].creditpoints);
            expect(searchCol.get('M1').get('lecturer')).toEqual(resultObject.modules[0].lecturer);
            expect(searchCol.get('M2').get('id')).toEqual(resultObject.modules[1].id);
            expect(searchCol.get('M2').get('name')).toEqual(resultObject.modules[1].name);
            expect(searchCol.get('M2').get('creditpoints')).toEqual(resultObject.modules[1].creditpoints);
            expect(searchCol.get('M2').get('lecturer')).toEqual(resultObject.modules[1].lecturer);
        });
        
        it("/modules", function () {
            searchCol.planId = null;
            searchCol.fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('api.studyplan.devel/modules?filter=testFilter1%2CtestFilter2%2CtestFilter3&testFilter1-min=0&testFilter1-max=20&testFilter2=abc&testFilter3=1');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify(resultObject)
            });
            expect(searchCol.get('M1').get('id')).toEqual(resultObject.modules[0].id);
            expect(searchCol.get('M1').get('name')).toEqual(resultObject.modules[0].name);
            expect(searchCol.get('M1').get('creditpoints')).toEqual(resultObject.modules[0].creditpoints);
            expect(searchCol.get('M1').get('lecturer')).toEqual(resultObject.modules[0].lecturer);
            expect(searchCol.get('M2').get('id')).toEqual(resultObject.modules[1].id);
            expect(searchCol.get('M2').get('name')).toEqual(resultObject.modules[1].name);
            expect(searchCol.get('M2').get('creditpoints')).toEqual(resultObject.modules[1].creditpoints);
            expect(searchCol.get('M2').get('lecturer')).toEqual(resultObject.modules[1].lecturer);
        });
    });
});