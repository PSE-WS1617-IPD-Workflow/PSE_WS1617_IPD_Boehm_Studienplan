goog.provide("edu.kit.informatik.studyplan.client.router.MainRouter");
/**
 * @constructor
 */
edu.kit.informatik.studyplan.client.router.MainRouter = Backbone.Router.extend(/**
@lends {edu.kit.informatik.studyplan.client.router.MainRouter.prototype}*/
    {
        /**
         * @type {edu.kit.informatik.studyplan.client.view.MainView}
         */
        view: null,
        initialize: function () {
            this.view=new edu.kit.informatik.studyplan.client.view.MainView();
        },
        routes: {
            "/plans/:id":  "",
            "":     "main"
        },
        main: function () {
            "use strict";
            console.info("[edu.kit.informatik.studyplan.client.router.MainRouter] main");
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
            },{parse:true});
            this.view.setContent(edu.kit.informatik.studyplan.client.view.components.uipanel.PlanList,
                                {
                planCollection: planCollection
            });
            this.view.render();
        }
    }
);