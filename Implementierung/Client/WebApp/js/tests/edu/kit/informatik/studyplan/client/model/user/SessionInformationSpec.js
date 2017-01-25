define(["studyplan"], function (client) {
    "use strict";
    describe("SessionInformation", function () {
        var sesInfo = null; //test
        beforeEach(function () {
            /**
             * @type{edu.kit.informatik.studyplan.client.model.user.SessionInformation}
             */
            sesInfo = client.model.user.SessionInformation.getInstance();
        });
        it("Should exist (not be null)", function () {
            expect(sesInfo).not.toBeNull();
        });
        it("Should generate random state", function () {
            expect(sesInfo.get('state')).toBeUndefined();
            sesInfo.generateState();
            expect(typeof sesInfo.get('state')).toEqual("string");
            expect(sesInfo.get('state').length).not.toBeGreaterThan(30);
        });
    });
});