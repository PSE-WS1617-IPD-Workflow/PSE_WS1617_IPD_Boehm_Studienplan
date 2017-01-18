define(["studyplan"],function(studyplan){
    describe("LanguageManager", function () {
        "use strict";
        var LM = null; //test
        beforeAll(function () {
            console.log(studyplan.model.system.LanguageManager);
            LM = studyplan.model.system.LanguageManager.getInstance();
        });
        it("Instance should not be null", function () {
            expect(LM).not.toBeNull();//test
        });
    });
});