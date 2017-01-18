console.error("test");
describe("LanguageManager", function () {
    "use strict";
    var LM = null;
    beforeAll(function () {
        LM = edu.kit.informatik.studyplan.model.system.LanguageManager.getInstance();
    });
    it("Instance should not be null", function () {
        expect(LM).toBeNull();
    });
});