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
        },
        sass: {
            dist: {
              files: {
                'htdocs/css/main.css': 'scss/main.scss'
              }
            }
      },
      karma: {
          unit: {
            configFile: 'karma.conf.js',
            singleRun : true,
            logLevel: 'ERROR'
          }
      }


    });
    // These plugins provide necessary tasks.
    grunt.loadNpmTasks('grunt-contrib-jst');
    grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.loadNpmTasks('grunt-karma');


    grunt.registerTask('default',['sass','jst']);
    grunt.registerTask('jst', []);
    grunt.registerTask('test',['karma']);
}