/**
 * @implements {AppException}
 * @constructor
 * @param {string} keyParam The key of the template
 */
function TemplateNotFoundException(keyParam) {
    "use strict";
    this.key = keyParam;
}