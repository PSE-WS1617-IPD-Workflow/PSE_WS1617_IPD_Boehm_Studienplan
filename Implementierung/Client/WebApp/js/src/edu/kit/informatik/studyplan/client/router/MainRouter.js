goog.provide("edu.kit.informatik.studyplan.client.router.MainRouter");
/**
 * @class
 * @name edu.kit.informatik.studyplan.client.router.MainRouter
 * @extends {Backbone.Router}
 */
edu.kit.informatik.studyplan.client.router.MainRouter = (function () {
    "use strict";
    var instance = null;
    /**
     * @constructor
     * @name edu.kit.informatik.studyplan.client.router.MainRouter
     */
    var Constructor = Backbone.Router.extend(
        {
            /**
             * @type {edu.kit.informatik.studyplan.client.view.MainView}
             */
            view: null,
            /**
             * @this {edu.kit.informatik.studyplan.client.router.MainRouter}
             */
            initialize: function () {
                console.log("[edu.kit.informatik.studyplan.client.router.MainRouter] initialize");
                this.view = new edu.kit.informatik.studyplan.client.view.MainView();
                this.view.setHeader(edu.kit.informatik.studyplan.client.view.subview.Header, {
                    sessionInformation: edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance()
                });
                this.view.render();
            },
            routes: function () {
                if (!edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().isLoggedIn()) {
                    return {
                        "processLogin": "handleLogin",
                        "*notFound": "loginPage"
                    };
                }
                return {
                    "plans/:planId":  "editPage",
                    "compare/:planId1/:planId2": "comparisonPage",
                    "plans/:planId/generate": "generationWizard",
                    "processLogin": "handleLogin",
                    "login": "loginPage",
                    "profile": "showProfile",
                    "signup": "signUpWizard",
                    "":     "mainPage",
                    "*notFound": "notFound"
                };
            },
            mainPage: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] mainPage");
                var planCollection = new edu.kit.informatik.studyplan.client.model.plans.PlanCollection({
                    plans   :   [
                        {
                            id  :   'P1',
                            name:   'Hermines Studienplan',
                            status: 'valid',
                            'creditpoints-sum'  :   9000
                        },
                        {
                            id  :   'P2',
                            name:   'Harrys Studienplan',
                            status: 'not-verified',
                            'creditpoints-sum': 180
                        }, {
                            id  :   'P3',
                            name:   'Rons Studienplan',
                            status: 'invalid',
                            'creditpoints-sum': 100
                        }
                    ]
                }, {parse: true});
                this.view.setContent(edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList,
                    {
                        planCollection: planCollection
                    });
                this.view.render();
            },
            editPage: function (planId) {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] editPage");

            },
            comparisonPage: function (planId1, planId2) {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] comparisonPage");

            },
            generationWizard: function (planId) {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] generationWizard");
            },
            handleLogin: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] handleLogin");

            },
            loginPage: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] loginPage");
                this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.LoginPage, {});
                this.view.render();
            },
            showProfile: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] showProfile");

            },
            signUpWizard: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] signUpWizard");

            },
            notFound: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] notFound");

            }
        }
    );
    return {
        getInstance: function () {
            if (instance === null) {
                instance = new Constructor();
            }
            return instance;
        }
    };
}());