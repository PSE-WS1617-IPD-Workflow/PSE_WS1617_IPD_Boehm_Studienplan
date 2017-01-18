define(["studyplan"], function (studyplan) {
    "use strict";
    describe("EventBus", function () {
        var eventBus = null; //test
        beforeEach(function () {
            /**
             * @type{Backbone.Event}
             */
            eventBus = studyplan.model.system.EventBus.getInstance();
        });
        it("Instance should not be null", function () {
            expect(eventBus).not.toBeNull();//test
        });
        it("should execute event listeners", function () {
            var listenerObject = { listener : function () {} };
            spyOn(listenerObject, 'listener');
            eventBus.on("testEvent", listenerObject.listener);
            eventBus.trigger("testEvent");
            expect(listenerObject.listener).toHaveBeenCalled();
        });
    });
});