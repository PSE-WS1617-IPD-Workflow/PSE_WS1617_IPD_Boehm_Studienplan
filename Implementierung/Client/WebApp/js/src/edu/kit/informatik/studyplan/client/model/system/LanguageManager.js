goog.provide("edu.kit.informatik.studyplan.model.system.LanguageManager");

edu.kit.informatik.studyplan.model.system.LanguageManager = (function () {
    "use strict";
    /**
     * @define {String}
     */
    var LANGUAGE_MANAGER_FALLBACK_LANGUAGE = "de";
    /**
     * @type {LanguageManagerClass}
     * @private
     */
    var instance = nul;
    /**
     * @constructor
     * @private
     */
    var LanguageManager = function () {
        /**
         * @type {Object<String,Object<String, String>>}
         */
        this.messages = {};
        /**
         * @type {String}
         */
        this.language = LANGUAGE_MANAGER_FALLBACK_LANGUAGE;
        /**
         * @param {String} lang The language key to be set
         */
        this.setLanguage = function (lang) {
            this.language = lang;
        };
        /**
         * @param {String} key The key of the message.
         * @return {String} The message in the set language, in the fallback language or the key (if the message does not exist)
         */
        this.getMessage = function (key) {
            if (typeof this.messages[this.language][key] !== "undefined") {
                return this.messages[this.language][key];
            }
            if (typeof this.messages[LANGUAGE_MANAGER_FALLBACK_LANGUAGE][key] !== "undefined") {
                return this.messages[LANGUAGE_MANAGER_FALLBACK_LANGUAGE][key];
            }
            return key;
        };
    };
    return (function () {
        /**
         * @return {LanguageManager} The LanguageManager Object
         */
        this.getInstance = function () {
            if (instance === null) {
                instance = new LanguageManager();
            }
        };
    }());
}());