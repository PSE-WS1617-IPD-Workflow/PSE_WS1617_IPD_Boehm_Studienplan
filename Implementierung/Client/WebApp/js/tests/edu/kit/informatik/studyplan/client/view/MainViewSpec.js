define(["studyplan"], function (studyplan) {
    "use strict";
    describe("MainView", function () {
        var mainView = null; //test
        var headerWrapper;
        var contentWrapper;
        var renderWrapper;
        var options;
        beforeEach(function () {
            /**
             * @type{edu.kit.informatik.studyplan.client.view.MainView}
             */
            mainView = new studyplan.view.MainView();
            headerWrapper = { headerConstructor : function (options) {} };
            contentWrapper = { contentConstructor : function (options) {} };
            renderWrapper = { render : function () {} };
            options = {test: "foo"};
        });
        it("Instance should not be null", function () {
            expect(mainView).not.toBeNull();//test
        });
        it("Should initialise Header", function () {
            spyOn(headerWrapper, 'headerConstructor');
            mainView.setHeader(headerWrapper.headerConstructor, options);
            expect(headerWrapper.headerConstructor).toHaveBeenCalled();
            expect(headerWrapper.headerConstructor.calls.argsFor(0)[0].test).toEqual("foo");
        });
        it("Should initialise Content", function () {
            spyOn(contentWrapper, 'contentConstructor');
            mainView.setHeader(contentWrapper.contentConstructor, options);
            expect(contentWrapper.contentConstructor).toHaveBeenCalled();
            expect(contentWrapper.contentConstructor.calls.argsFor(0)[0].test).toEqual("foo");
        });
        it("Should call header and content render function on render", function () {
            spyOn(renderWrapper,"render");
            spyOn(headerWrapper, 'headerConstructor').and.returnValue(renderWrapper);
            spyOn(contentWrapper, 'contentConstructor').and.returnValue(renderWrapper);
            mainView.setHeader(headerWrapper.headerConstructor,options);
            mainView.setContent(contentWrapper.contentConstructor, options);
            mainView.render();
            expect(renderWrapper.render.calls.count()).toEqual(2);
        });
    });
});