/*
 * grunt-contrib-jst
 * http://gruntjs.com/
 *
 * Copyright (c) 2016 Tim Branyen, contributors
 * Licensed under the MIT license.
 */
'use strict';
module.exports = function (grunt) {
    grunt.initConfig({
        jst: {
            compile: {
                options: {
                    namespace : 'templates',
                    templateSettings: {
                        variable: 'obj'
                    }
                }
                , files: {
                    'tmp/jst.js': ['templates/*.html']
                }
            }
        }
    });
    // These plugins provide necessary tasks.
    grunt.loadNpmTasks('grunt-contrib-jst');
    grunt.registerTask('default', ['jst']);
}