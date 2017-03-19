goog.provide("edu.kit.informatik.studyplan.client.model.system.TourManager");
/**
 * Singleton, which contains the Tour
 */
edu.kit.informatik.studyplan.client.model.system.TourManager = (function () {
    "use strict";
    /**
     * @type {TourManager }
     * @private
     */
    var instance = null;

    /**
     * The language manager class
     * @constructor
     * @private
     */
    var TourManager = function () {
        /**
         * All messages available in the language manager
         * @type {Object<string,function():Array<Object>>}
         */
        this.tours = {};
        /**
         * @param {string} key The key of the tour.
         */
        this.getTour = function (key) {
            if (typeof this.tours[key] !== "undefined") {
                return this.tours[key];
            } else {
                return null;
            }
        };
    };

    return {
        /**
         * Method to retrieve the current instance of the TourManager
         * @return {TourManager} The TourManager Object
         */
        getInstance: function () {
            if (instance === null) {
                instance = new TourManager();
            }
            return instance;
        }
    };
}());