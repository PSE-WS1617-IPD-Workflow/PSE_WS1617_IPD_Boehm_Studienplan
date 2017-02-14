goog.provide("edu.kit.informatik.studyplan.client.model.system.TemplateManager");
/**
 * Model class which contains all templates used by the views.
 * The templates are being automatically parsed and included at compile time.
 * This reduces loading time as the templates are not being loaded and parsed during runtime
 */
edu.kit.informatik.studyplan.client.model.system.TemplateManager = (function () {
    "use strict";
    /**
     * @type {TemplateRegistry}
     * @private
     */
    var instance = null;
    /**
     * The actual TemplateRegistry class
     * @private
     * @constructor
     */
    function TemplateRegistry() {
        /**
         * Variable storing all of the templates
         * @protected
         * @type {Object<string,function(Object):string>}
         */
        this.templates = {};
        /**
         * Method for adding templates. This should never be necessary, but just in case: here it is...
         * @public
         * @param {string} key The key of the template
         * @param {string} html The HTML content of the template
         */
        this.addTemplate = function (key, html) {
            this.templates[key] = _.template(html);
        };
        /**
         * Method to retrieve a template based on it's full path starting at /resources/...
         * @public
         * @param{string} key The key of the template
         * @throws {TemplateNotFoundException}
         */
        this.getTemplate = function (key) {
            if (typeof this.templates[key] !== "undefined") {
                return (function (options) {
                    if (typeof options === "undefined") {
                        options = {};
                    }
                    var params = _.clone(options);
                    params["LM"] = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
                    return this.templates[key](params);
                }).bind(this);
            } else {
                console.error("[edu.kit.informatik.studyplan.client.model.system.TemplateManager] Cannot find template '" + key + "'.");
            }
        };
    }
    return {
        /** 
         * @return {TemplateRegistry}
         */
        getInstance : function () {
            if (instance === null) {
                instance = new TemplateRegistry();
            }
            return instance;
        }
    };
}());