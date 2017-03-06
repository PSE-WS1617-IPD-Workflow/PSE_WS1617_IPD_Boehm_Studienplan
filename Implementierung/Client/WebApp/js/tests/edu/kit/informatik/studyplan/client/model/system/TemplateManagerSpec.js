define(["studyplan", "underscore"], function (studyplan, _) {
    "use strict";
    describe("TemplateManager", function () {
        var TM = null; //test
        beforeEach(function () {
            TM = studyplan.model.system.TemplateManager.getInstance();
            _.extend(TM.templates, {
                "test": _.template("<foo><bar><%=obj.test%></bar></foo>")
            });
        });
        it("Instance should not be null", function () {
            expect(TM).not.toBeNull(); //test
        });
        it("should return the template html", function () {
            expect(TM.getTemplate("test")({
                test: "huhu"
            })).toEqual("<foo><bar>huhu</bar></foo>")
        });
    });
});