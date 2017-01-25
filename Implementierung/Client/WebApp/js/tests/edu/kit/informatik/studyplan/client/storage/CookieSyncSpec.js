define(["studyplan", "cookies"], function (client, Cookies) {
    "use strict";
    describe("CookieSync", function () {
        var sesInfo = null;
        beforeEach(function () {
            sesInfo = client.model.user.SessionInformation.getInstance();
        });
        it("should save information in cookie", function () {
            sesInfo.generateState();
            sesInfo.set('access_token', 'zweiundvierzig');
            client.storage.CookieSync("create", sesInfo);
            var res = Cookies.getJSON(_.result(sesInfo,'url'));
            expect(res.access_token).toEqual(sesInfo.get('access_token'));
            expect(res.state).toEqual(sesInfo.get('state'));
        });
        if("should retrieve information in cookie", function () {
            
        });
    });
});