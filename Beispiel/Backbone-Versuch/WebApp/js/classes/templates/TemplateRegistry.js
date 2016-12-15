goog.provide("StudyplanApp.Templating.TemplateRegistry");
StudyplanApp.Templating.TemplateRegistry = (function () {
    "use strict";
    /**
     * @type{StudyplanApp.Templating.TemplateRegistryInterface}
     */
    var singleton = null;
    /**
     * @private
     * @constructor
     * @implements {StudyplanApp.Templating.TemplateRegistryInterface}
     */
    function TemplateRegistry() {
        /**
         * Variable storing all of the templates
         * @protected
         * @type {Object<string,function(Object):string>}
         */
        this.templates = {};
        /**
         * @public
         * @param {string} key The key of the template
         * @param {string} html The HTML content of the template
         */
        this.addTemplate = function (key, html) {
            this.templates[key] = _.template(html);
        };
        /**
         * @public
         * @param{string} key The key of the template
         * @throws {TemplateNotFoundException}
         */
        this.getTemplate = function (key) {
            if (typeof this.templates[key] !== "undefined") {
                return this.templates[key];
            } else {
                console.error("[TemplateFactory] Cannot find template '" + key + "'.");
                throw new StudyplanApp.Exceptions.TemplateNotFoundException(key);
            }
        };
    }
    return {
        /** 
         * @return {StudyplanApp.Templating.TemplateRegistryInterface}
         */
        getInstance : function () {
            if (singleton === null) {
                singleton = new TemplateRegistry();
            }
            return singleton;
        }
    };
}());


