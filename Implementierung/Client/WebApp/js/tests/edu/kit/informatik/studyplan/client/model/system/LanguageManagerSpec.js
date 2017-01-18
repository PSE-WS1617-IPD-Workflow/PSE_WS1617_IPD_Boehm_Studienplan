define(["studyplan"], function (studyplan) {
    "use strict";
    describe("LanguageManager", function () {
        var LM = null; //test
        beforeEach(function () {
            LM = studyplan.model.system.LanguageManager.getInstance();
            LM.messages = {
                "de" : {
                    "test1" : "Test Nachricht 1",
                    "test2" : "Test Nachricht 2"
                },
                "en" : {
                    "test1" : "Test Message 1"
                }
            };
        });
        it("Instance should not be null", function () {
            expect(LM).not.toBeNull();//test
        });
        it("should use german language on default", function () {
            expect(LM.getMessage("test1")).toMatch('Test Nachricht 1');
            expect(LM.getMessage("test2")).toMatch('Test Nachricht 2');
        });
        it("should return the key if the message does not exist", function () {
            expect(LM.getMessage("no_message")).toMatch("no_message");
        });
        it("should return english language when language is set to 'en'", function () {
            LM.setLanguage("en");
            expect(LM.getMessage("test1")).toMatch("Test Message 1");
        });
        it("should use message in default language if english message does not exist", function () {
            LM.setLanguage("en");
            expect(LM.getMessage("test2")).toMatch("Test Nachricht 2");
        });
        it("should use message in default language if language does not exist", function () {
            LM.setLanguage("Parsel");
            expect(LM.getMessage("test1")).toMatch("Test Nachricht 1");
        });
    });
});