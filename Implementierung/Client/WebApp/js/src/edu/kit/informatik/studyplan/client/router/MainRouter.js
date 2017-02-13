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
                this.on("route", function (route, params) {
                    if (!edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().isLoggedIn()) {
                        if (route != "handleLogin" && route != "loginPage") {
                            this.navigate("/login", {trigger: true});
                        }
                    }
                    var student = new edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('student');
                        student.fetch({
                            success: function () {
                                if(student.get('studyStartYear') === null) {
                                    student.loaded = true;
                                    this.navigate("/signup", {trigger: true});
                                }
                            }.bind(this)
                        });
                }.bind(this));
            },
            routes:{
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
                },
            /**
             * Loading plan collection (main page)
             * @url /
             */
            mainPage: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] mainPage");
                this.showLoading();
                var planCollection = new edu.kit.informatik.studyplan.client.model.plans.PlanCollection();
                planCollection.fetch({
                    success: function () {
                        this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.MainPage,
                            {
                                planCollection: planCollection
                            });
                        this.view.render();
                        this.hideLoading();
                    }.bind(this)
                });
                
            },
            /**
             * Loading single plan (edit page)
             * @url /plans/:planId
             */
            editPage: function (planId) {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] editPage");
                this.showLoading();
                var plan = new edu.kit.informatik.studyplan.client.model.plans.Plan({id: planId});
                var info = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance();
                var student = info.get('student');
                student.fetch({
                    success: function () {
                        plan.fetch({
                            success: function () {
                                this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.PlanEditPage, {
                                    plan:  plan
                                });
                                this.hideLoading();
                            }.bind(this)
                        });
                    }.bind(this)
                });
            },
            /**
             * Loading 2 plans (comparison page)
             * @url /compare/:plan1/:plan2
             */
            comparisonPage: function (planId1, planId2) {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] comparisonPage");
                this.showLoading();
                this.hideLoading();
                throw new Error("Not implemented");
            },
            /**
             * Initalising generation wizard
             * @url /plans/:planId/generate
             */
            generationWizard: function (planId) {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] generationWizard");
                this.showLoading();
                var info = new edu.kit.informatik.studyplan.client.model.system.ProposalInformation();
                var plan = new edu.kit.informatik.studyplan.client.model.plans.Plan({id: planId});
                plan.fetch({
                    // Callback: Original plan loaded
                    success: function () {
                        this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.WizardPage, {
                            firstPage: new edu.kit.informatik.studyplan.client.view.components.uipanel.GenerationWizardComponent1({
                                plan: planId,
                                information: info
                            }),
                            // Callback: Generation wizard completed
                            onFinish: function (lastView) {
                                this.showLoading();
                                var proposedPlan = plan.retrieveProposedPlan();
                                proposedPlan.setInfo(info);
                                proposedPlan.fetch({
                                    // Callback: Generated plan loaded
                                    success: function () {
                                        this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.PlanEditPage, {
                                            plan:  proposedPlan,
                                            proposed: true,
                                            isAddable: false 
                                        });
                                        this.hideLoading();
                                    }.bind(this)
                                });
                            }.bind(this)
                        });
                        this.hideLoading();
                    }.bind(this)
                });
            },
            /**
             * Handling login response from API
             * @url /processLogin
             */
            handleLogin: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] handleLogin");
                this.showLoading();
                var redirectData = this.processData(window.location.hash.substr(1));
                var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
                // If invalid state, return error notification and stop
                if (redirectData["state"] !== edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('state')) {
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
                // If api returned error, return error notification and stop
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
                // This actually seems to be a legit request...
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
                    this.navigate("/login", {trigger: true});
                    edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
                        .add(
                            new edu.kit.informatik.studyplan.client.model.system.Notification({
                                title: LM.getMessage("realAuthEndTitle"),
                                text: LM.getMessage("realAuthEndText"),
                                wasShown: false,
                                type: "error"
                            })
                        );
                }.bind(this),redirectData["expires_in"]*1000);
                console.log("[edu.kit.informatik.studyplan.client.router.MainRouter] authentication successful");
                info.set('student', new edu.kit.informatik.studyplan.client.model.user.Student());
                var student = info.get('student');
                student.fetch({
                    success: function () {
                        edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
                            .add(
                                new edu.kit.informatik.studyplan.client.model.system.Notification({
                                    title: LM.getMessage("loginSuccessfulTitle"),
                                    text: LM.getMessage("loginSuccessfulText"),
                                    wasShown: false,
                                    type: "success"
                                })
                            );
                        if(student.get('studyStartYear') === null) { //TODO wann eigentlich? / wie sieht die Antwort aus?
                            this.navigate("/signup", {trigger: true});
                        } else {
                            this.navigate("/", {trigger: true});
                        }
                    }.bind(this)
                });
            },
            /**
             * Show login page
             * @url /login
             */
            loginPage: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] loginPage");
                this.showLoading();
                this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.LoginPage, {});
                this.hideLoading();
            },
            /**
             * Show profile page
             * @url /profile
             */
            showProfile: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] showProfile");
                this.showLoading();
                var student = new edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('student');
                student.fetch({
                    success: function () {
                        var plan = student.get('passedModules').toPlan();
                        this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.ProfilPage, {
                            plan: plan
                        });
                        this.view.render();
                        // Do stuff heresignUpWizard
                        this.hideLoading();
                    }.bind(this)
                });
            },
            /**
             * Initalize signup wizard
             * @url /signup
             */
            signUpWizard: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] signUpWizard");
                this.showLoading();
                var student = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('student');
                var done = function () {
                        this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.WizardPage, {
                            firstPage: new edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1({
                               student: student
                            }),
                            // Callback: wizard finished
                            onFinish: function (lastView) {
                                this.showLoading();
                                student.save(null,{
                                    // Callback student saved
                                    success:
                                        function(){   
                                            this.hideLoading();
                                            this.navigate("/", {trigger:true});
                                        }.bind(this)
                                });
                            }.bind(this)
                        });
                        this.hideLoading();
                    }.bind(this);
                if(student.loaded){
                    done();
                }else{
                    student.fetch({
                        // Callback: student loaded
                        success: done
                    });
                }
            },
            /**
             * Show not found page
             * /[non existent url]
             */
            notFound: function () {
                console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] notFound");
                this.showLoading();
                this.view.setContent(edu.kit.informatik.studyplan.client.view.subview.NotFoundPage, {});
                this.hideLoading();
            },
            /**
             * logsout user and redirects to /login
             * @url /logout
             */
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
            /**
             * Show loading screen
             */
            showLoading: function () {
                this.view.showLoading();
            },
            /**
             * Hide loading screen
             */
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