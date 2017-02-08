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
                    "logout": "logoutPage",
                    "*notFound": "notFound"
                };
            },
            mainPage: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] mainPage");
                this.showLoading();
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
                this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.MainPage,
                    {
                        planCollection: planCollection
                    });
                this.view.render();
                this.hideLoading();
            },
            editPage: function (planId) {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] editPage");
                this.showLoading();
                var plan = new edu.kit.informatik.studyplan.client.model.plans.Plan({
                    plan    :   {
                        id  :   'P3',
                        name:   'Rons Studienplan',
                        status: 'invalid',
                        'creditpoints-sum': 100,
                        modules :   [
                            {
                                id          :   "M1",
                                name        :   "Bayrisch",
                                creditpoints:   7,
                                lecturer    :   "Aloisius",
                                preference  :   "positive",
                                semester    :   3
                            },
                            {
                                id          :   "M2",
                                name        :   "Schwäbisch",
                                creditpoints:   5,
                                lecturer    :   "Maultaschius",
                                preference  :   "negative",
                                semester    :   5
                            }
                        ]
                    }
                },{parse:true});/*{
                    id: planId,
                    
                });*/
                /*plan.fetch({
                    success: function () {*/
                        this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.PlanEditPage, {
                            plan:  plan
                        });
                        this.hideLoading();
                    /*}
                });*/
            },
            comparisonPage: function (planId1, planId2) {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] comparisonPage");
                this.showLoading();
                // Do stuff here
                this.hideLoading();
            },
            generationWizard: function (planId) {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] generationWizard");
                this.showLoading();
                var plan = new edu.kit.informatik.studyplan.client.model.palns.Plan({id: planId});
                var info = new edu.kit.informatik.studyplan.client.model.system.ProposalInformation()
                var self = this;
                plan.fetch({
                    success: function () {
                        self.view.setContent(edu.kit.informatik.studyplan.client.view.subview.WizardPage, {
                            firstPage: new edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent1({
                                plan: plan,
                                information: info
                            }),
                            onFinish: function () {
                                /*
                                *todo:
                                *Generierungsaufruf
                                */
                            }
                        });
                    // Do stuff here
                        self.hideLoading();
                    }
                });
            },
            handleLogin: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] handleLogin");
                this.showLoading();
                // Do stuff here
                this.hideLoading();
            },
            loginPage: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] loginPage");
                this.showLoading();
                this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.LoginPage, {});
                this.hideLoading();
            },
            showProfile: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] showProfile");
                this.showLoading();
                // Do stuff here
                this.hideLoading();
            },
            signUpWizard: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] signUpWizard");
                this.showLoading();
                // Do stuff here
                this.hideLoading();
            },
            notFound: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] notFound");
                this.showLoading();
                // Do stuff here
                this.hideLoading();
            },
            logoutPage: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] logoutPage");
                this.showLoading();
                // Do stuff here
                this.hideLoading();
            },
            showLoading: function () {
                this.view.showLoading();
            },
            hideLoading: function () {
                this.view.hideLoading();
            }
        }
    );
    return {
        /**
         * @return {edu.kit.informatik.studyplan.client.router.MainRouter}
         */
        getInstance: function () {
            if (instance === null) {
                instance = new Constructor();
            }
            return instance;
        }
    };
}());