define(["studyplan"], function (client) {
    "use strict";
    var accessToken = 'weiredlyUnrandomAccessToken';
    describe("OAuthSync", function () {
        var plan = new client.model.plans.Plan({
            plan:  {
                name : "Der ultimative Studienplan"
            }}, {parse : true, url : 'api.studyplan.devel/plans'});
        beforeEach(function () {
            jasmine.Ajax.install();
            client.model.user.SessionInformation.getInstance().set('access_token', accessToken);
        });
        afterEach(function () {
            jasmine.Ajax.uninstall();
        });
        it("Should include header", function () {
            client.storage.OAuthSync("create",plan,{});
            expect(jasmine.Ajax.requests.mostRecent().url).toBe('api.studyplan.devel/plans');
            //console.log(jasmine.Ajax.requests.mostRecent().eventBus.source);
            expect(jasmine.Ajax.requests.mostRecent().requestHeaders.Authorization).toEqual("Bearer "+accessToken);
            //expect(jasmine.Ajax.requests.mostRecent().requestHeaders.Authorization).toEqual("Bearer asdf "+accessToken);
        });
    });
});