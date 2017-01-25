define(["studyplan"], function (client) {
    "use strict";
    
    describe("NotificationCollection", function () {
        var notifCol = null;
        beforeEach(function () {
            notifCol = client.model.system.NotificationCollection.getInstance;
        });
        it("Should not be null", function () {
            expect(notifCol).not.toBeNull();
        });
    });
});