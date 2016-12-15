goog.provide("StudyplanApp.Exceptions.TemplateNotFoundException");
/**
 * @implements {StudyplanApp.Exceptions.AppException}
 * @constructor
 * @param {string} keyParam The key of the template
 */
StudyplanApp.Exceptions.TemplateNotFoundException = function (keyParam) {
    "use strict";
    this.key = keyParam;
};