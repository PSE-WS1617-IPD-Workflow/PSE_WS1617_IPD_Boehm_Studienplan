define(["studyplan"], function (client) {
    "use strict";
    var searchCol, filterCol, resultObject;
    beforeEach(function () {
        searchCol = new client.model.system.SearchCollection([
            {
                id : 'M1'
            }
        ], {planId : "abcdef"});
        
        resultObject = {
            module : {
                id          :   "M1",
                name        :   "Bayrisch",
                "cycle-type":   "WS",
                preference  :   "positive",
                categories  :   [{id : 1, name : "Tolle Sprachen"}, {id : 2, name : "Geheimnisse, die die Preißn nie lernen"}],
                creditpoints:   7,
                lecturer    :   "Aloisius",
                description :   "[Insert pseudo bayrisch here]",
                constraints :   [
                    {
                        name : "abc",
                        first: {id:"M1"},
                        second: {id:"M2"},
                        type:   "Voraussetzung"
                    }
                ]   
            }
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
        it("/plans/abcdef/modules/M1", function () {
            searchCol.get('M1').fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('api.studyplan.devel/plans/abcdef/modules/M1');
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify(resultObject)
            });
            expect(searchCol.get('M1').get('id')).toEqual(resultObject.module.id);
            expect(searchCol.get('M1').get('name')).toEqual(resultObject.module.name);
            expect(searchCol.get('M1').get('categories')[0]).toEqual(resultObject.module.categories[0].name);
            expect(searchCol.get('M1').get('creditpoints')).toEqual(resultObject.module.creditpoints);
            expect(searchCol.get('M1').get('lecturer')).toEqual(resultObject.module.lecturer);
            expect(searchCol.get('M1').get('description')).toEqual(resultObject.module.description);
            expect(searchCol.get('M1').get('constraints')[0].get('first').get('id')).toEqual('M1');
            expect(searchCol.get('M1').get('preference').get('preference')).toEqual('positive');
        });
        
        it("/modules/M1", function () {
            searchCol.planId = null;
            searchCol.get('M1').fetch();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('api.studyplan.devel/modules/M1');
            
        });
        
        it("/plans/abcdef/modules/M1/preference", function () {
            searchCol.get('M1').fetch();
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify(resultObject)
            });
            searchCol.get('M1').get('preference').set('preference','negative');
            searchCol.get('M1').get('preference').save();
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify({
                    id  :   "M1",
                    preference  :   "negative"
                })
            });
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('api.studyplan.devel/plans/abcdef/modules/M1/preference');
            expect(jasmine.Ajax.requests.mostRecent().method).toBe('PUT');
            expect(jasmine.Ajax.requests.mostRecent().data()).toEqual({module : {id : 'M1', preference : 'negative'}});
            expect(searchCol.get('M1').get('preference').get('module')).toEqual(searchCol.get('M1'))
        });
        it("PUT /plans/abcdef/modules/M1", function () {
            searchCol.get('M1').fetch();
            jasmine.Ajax.requests.mostRecent().respondWith({
                "status"    :   200,
                "contentType"   :   "application/json",
                "responseText"  :   JSON.stringify(resultObject)
            });
            searchCol.get('M1').set('semester',4);
            searchCol.get('M1').save();
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('api.studyplan.devel/plans/abcdef/modules/M1');
            expect(jasmine.Ajax.requests.mostRecent().method).toBe('PUT');
            expect(jasmine.Ajax.requests.mostRecent().data()).toEqual({module : {id : 'M1', semester : 4}});
        });
    });
});