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
                this.on("route", function(route, params) {
                    if (!edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().isLoggedIn()) {
                        if (route!="handleLogin" && route!="loginPage") {
                            this.navigate("/login", {trigger: true});
                        }
                    }
                });
            },
            routes: 
            /**
            *todo: server wiedereinschalten (hierdrunter derzeit auskommentiert.)
            */
            /*function () {
                console.log("[edu.kit.informatik.studyplan.client.router.MainRouter] building route hash");
                /if (!edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().isLoggedIn()) {
                    return {
                        "processLogin": "handleLogin",
                        "*notFound": "loginPage"
                    };
                }
                return */{
                    "plans/:planId":  "editPage",
                    "compare/:planId1/:planId2": "comparisonPage",
                    "plans/:planId/generate": "generationWizard",
                    "processLogin": "handleLogin",
                    "login": "loginPage",
                    "profile": "showProfile",
                    "signup": "signUpWizard",
                    "":     "mainPage",
                    "/":     "mainPage",
                    "logout": "logoutPage",
                    "*notFound": "notFound"
                }/*;
            }*/,
            mainPage: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] mainPage");
                this.showLoading();
                /*var planCollection = new edu.kit.informatik.studyplan.client.model.plans.PlanCollection({
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
                */
                var planCollection = new edu.kit.informatik.studyplan.client.model.plans.PlanCollection();
                var self = this;
                planCollection.fetch({
                    success: function () {
                        self.view.setContent(edu.kit.informatik.studyplan.client.view.subview.MainPage,
                            {
                                planCollection: planCollection
                            });
                        self.view.render();
                        self.hideLoading();
                    }
                });
                
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
                            },
                            {
                                id : 22,
                                semester : 4,
                                name : "Magische Tierwesen",
                                creditpoints: 18,
                                preference  :   "negative",
                                lecturer: "Hagrid",
                            },
                            {
                                id : 21,
                                name : "Zaubertränke",
                                semester : 4,
                                creditpoints:700,
                                preference  :   "negative",
                                lecturer: "Snape",
                            },
                            {
                                id : 20,
                                name : "Zaubertränke 2",
                                semester: 4,
                                creditpoints:700,
                                preference  :   "negative",
                                lecturer: "Snape"
                            },
                            {
                                id : 30,
                                name : "Zaubertränke 3",
                                semester: 4,
                                creditpoints:700,
                                preference  :   "negative",
                                lecturer: "Snape",
                            },
                            {
                                id : 40,
                                name : "Zaubertränke 4",
                                semester: 4,
                                creditpoints:700,
                                preference  :   "negative",
                                lecturer: "Snape",
                            }
                        ]
                    }
                }, {parse: true});/*{
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
                var plan = new edu.kit.informatik.studyplan.client.model.plans.Plan({id: planId});
                var info = new edu.kit.informatik.studyplan.client.model.system.ProposalInformation()
                var self = this;
                /*plan.fetch({
                    success: function () {*/
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
                    /*}
                });*/
            },
            handleLogin: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] handleLogin");
                this.showLoading();
                var redirectData = this.processData(window.location.hash.substr(1));
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] redirectData:")
                console.info(redirectData);
                var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
                var self = this;
                if (redirectData["state"]
                        !==edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('state')) {
                    edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
                        .add(
                            new edu.kit.informatik.studyplan.client.model.system.Notification({
                                title: LM.getMessage("invalidStateTitle"),
                                text: LM.getMessage("invalidStateText"),
                                wasShown: false,
                                type: "error"
                            })
                        );
                    this.navigate("/login", {trigger: true});
                    return;
                }
                if (redirectData["error"]) {
                    edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
                        .add(
                            new edu.kit.informatik.studyplan.client.model.system.Notification({
                                title: LM.getMessage("authError"+redirectData["error"]+"Title"),
                                text: LM.getMessage("authError"+redirectData["error"]+"Text"),
                                wasShown: false,
                                type: "error"
                            })
                        );
                    this.navigate("/login", {trigger: true});
                    return;
                }
                var info = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance();
                info.set('access_token',redirectData["access_token"]);
                info.save();
                window.setTimeout(function () {
                    edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
                        .add(
                            new edu.kit.informatik.studyplan.client.model.system.Notification({
                                title: LM.getMessage("authEndTitle"),
                                text: LM.getMessage("authEndText"),
                                wasShown: false,
                                type: "error"
                            })
                        );
                }, (redirectData["expires_in"]*1000-(100*1000)));
                window.setTimeout(function () {
                    info.set('access_token', undefined);
                    info.save();
                    self.navigate("/login", {trigger: true});
                    edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
                        .add(
                            new edu.kit.informatik.studyplan.client.model.system.Notification({
                                title: LM.getMessage("realAuthEndTitle"),
                                text: LM.getMessage("realAuthEndText"),
                                wasShown: false,
                                type: "error"
                            })
                        );
                },redirectData["expires_in"]*1000);
                console.log("[edu.kit.informatik.studyplan.client.router.MainRouter] authentication successful");
                edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
                    .add(
                        new edu.kit.informatik.studyplan.client.model.system.Notification({
                            title: LM.getMessage("loginSuccessfulTitle"),
                            text: LM.getMessage("loginSuccessfulText"),
                            wasShown: false,
                            type: "success"
                        })
                    );
                this.navigate("/", {trigger: true});
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
                var filter = new edu.kit.informatik.studyplan.client.model.system.FilterCollection({
                    filters: []
                }, {parse: true});
                this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.ProfilPage, {});
                this.view.render();
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
                var info = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance();
                info.set('access_token',undefined);
                info.save();
                edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
                    .add(
                        new edu.kit.informatik.studyplan.client.model.system.Notification({
                            title: LM.getMessage("logoutSuccessfulTitle"),
                            text: LM.getMessage("logoutSuccessfulText"),
                            wasShown: false,
                            type: "success"
                        })
                    );
                this.hideLoading();
                this.navigate("/login", {trigger: true});
            },
            showLoading: function () {
                this.view.showLoading();
            },
            hideLoading: function () {
                this.view.hideLoading();
            },
            /**
             * Helper function which processes data in hash
             * @private
             * @param {String} params Parameters which should be parsed
             * @return {Object}
             */
            processData: function (params) {
                var split = params.split("&");
                var result = {};
                _.each(split, function(param) {
                    var parts = param.split("=",2);
                    result[parts[0]] = parts[1];
                });
                return result;
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