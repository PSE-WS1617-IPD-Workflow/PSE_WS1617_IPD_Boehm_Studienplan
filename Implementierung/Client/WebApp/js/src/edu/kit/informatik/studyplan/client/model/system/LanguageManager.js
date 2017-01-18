goog.provide("edu.kit.informatik.studyplan.client.model.system.LanguageManager");
/**
 * Singleton, which contains the Language Manager
 */
edu.kit.informatik.studyplan.client.model.system.LanguageManager = (function () {
    "use strict";
    /**
     * @private
     */
    var LANGUAGE_MANAGER_FALLBACK_LANGUAGE = "de";
    /**
     * @type {LanguageManagerClass}
     * @private
     */
    var instance = null;
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
    return {
        /**
         * @return {LanguageManager} The LanguageManager Object
         */
        getInstance : function () {
            if (instance === null) {
                instance = new LanguageManager();
            }
            return instance;
        }
    };
}());