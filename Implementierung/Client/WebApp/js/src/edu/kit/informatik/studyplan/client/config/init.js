goog.provide("edu.kit.informatik.studyplan.client.config.init");
edu.kit.informatik.studyplan.client.config.init = function () {
    "use strict";
    if (DEBUG_ALWAYS_LOGIN) {
        var sessionInfo = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance();
        sessionInfo.set('access_token', API_TOKEN);
        sessionInfo.set('student', new edu.kit.informatik.studyplan.client.model.user.Student({
            student: {
                discipline: {
                    id: 42
                },
                "study-start": {
                    "semester-type": "WS",
                    "year": 2015
                },
                "current-semester": 3,
                "passed-modules": [{
                        id: "M5",
                        name: "Blödes bestandenes Modul",
                        creditpoints: 7,
                        lecturer: "Aloisius",
                        preference: "positive",
                        semester: 1
                    },
                    {
                        id: "M6",
                        name: "Tolles bestandenes Modul",
                        creditpoints: 5,
                        lecturer: "Maultaschius",
                        preference: "negative",
                        semester: 2
                    }
                ]
            }
        }, {
            parse: true
        }));
    }
    $(function () {
        //console.info("[edu.kit.informatik.studyplan.client.config.init] Starting Studyplan...");
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance();
        Backbone.history.start({
            pushState: true
        });
        //console.info("[edu.kit.informatik.studyplan.client.config.init] Backbone.History.started:");
        //console.info(Backbone.History.started);

        edu.kit.informatik.studyplan.client.config.init.checkAvailability();
        window.setInterval(edu.kit.informatik.studyplan.client.config.init.checkAvailability, 20 * 1000);
    });
};
edu.kit.informatik.studyplan.client.config.init.isDown = false;
edu.kit.informatik.studyplan.client.config.init.checkAvailability = function () {
    var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
    var sendAuthentication = function (xhr) {
        xhr.setRequestHeader("Authorization", "Bearer " + edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('access_token'));
    }
    var options = {
        url: API_DOMAIN + "/",
        beforeSend: sendAuthentication,
        "crossDomain": true
    };
    options["success"] = function (data, textStatus, xhr) {
        if (edu.kit.informatik.studyplan.client.config.init.isDown != false) {
            edu.kit.informatik.studyplan.client.config.init.isDown = false;
            edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
                .add(
                    new edu.kit.informatik.studyplan.client.model.system.Notification({
                        title: LM.getMessage("connectionBackTitle"),
                        text: LM.getMessage("connectionBackText"),
                        wasShown: false,
                        type: "success"
                    })
                );
        }
    }
    options["error"] = function (xhr, textStatus, errorThrown) {
        if (edu.kit.informatik.studyplan.client.config.init.isDown != true) {
            edu.kit.informatik.studyplan.client.config.init.isDown = true;
            edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance()
                .add(
                    new edu.kit.informatik.studyplan.client.model.system.Notification({
                        title: LM.getMessage("connectionLostTitle"),
                        text: LM.getMessage("connectionLostText"),
                        wasShown: false,
                        type: "error"
                    })
                );
        }
    }
    Backbone.ajax(options);
}