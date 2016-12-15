goog.provide("StudyplanApp.Templating.TemplateRegistryInterface");
/**
 * @interface
 */
StudyplanApp.Templating.TemplateRegistryInterface = function () {};

/**
 * @public
 * @param{string} key The key of the template
 * @throws {TemplateNotFoundException}
 */
StudyplanApp.Templating.TemplateRegistryInterface.prototype.getTemplate = function (key) {};
/**
 * @public
 * @param {string} key The key of the template
 * @param {string} html The HTML content of the template
 */
StudyplanApp.Templating.TemplateRegistryInterface.prototype.addTemplate = function (key, html) {};
/**
 * @type {Object<string,function(Object):string>}
 */
StudyplanApp.Templating.TemplateRegistryInterface.templates;