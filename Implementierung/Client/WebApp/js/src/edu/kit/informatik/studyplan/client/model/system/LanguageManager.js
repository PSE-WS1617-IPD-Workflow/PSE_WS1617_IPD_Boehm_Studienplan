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
     * @type {LanguageManager }
     * @private
     */
    var instance = null;

    /**
     * The language manager class
     * @constructor
     * @private
     */
    var LanguageManager = function () {
        /**
         * All messages available in the language manager
         * @type {Object<string,Object<string, string>>}
         */
        this.messages = {};
        /**
         * @type {string}
         */
        this.language = LANGUAGE_MANAGER_FALLBACK_LANGUAGE;
        /**
         * @param {string} lang The language key to be set
         */
        this.setLanguage = function (lang) {
            this.language = lang;
        };
        /**
         * @param {string} key The key of the message.
         * @return {string} The message in the set language, in the fallback language or the key (if the message does not exist)
         */
        this.getMessage = function (key) {
            if (typeof this.messages[this.language] !== "undefined") {
                if (typeof this.messages[this.language][key] !== "undefined") {
                    return this.messages[this.language][key];
                }
            }
            if (typeof this.messages[LANGUAGE_MANAGER_FALLBACK_LANGUAGE] !== "undefined") {
                if (typeof this.messages[LANGUAGE_MANAGER_FALLBACK_LANGUAGE][key] !== "undefined") {
                    return this.messages[LANGUAGE_MANAGER_FALLBACK_LANGUAGE][key];
                }
            }
            return key;
        };
    };

    return {
        /**
         * Method to retrieve the current instance of the language manager
         * @return {LanguageManager} The LanguageManager Object
         */
        getInstance: function () {
            if (instance === null) {
                instance = new LanguageManager();
            }
            return instance;
        }
    };
}());