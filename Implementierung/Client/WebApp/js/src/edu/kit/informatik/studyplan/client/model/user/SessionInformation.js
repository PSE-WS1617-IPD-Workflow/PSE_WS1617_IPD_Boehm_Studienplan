goog.provide("edu.kit.informatik.studyplan.client.model.user.SessionInformation");
/**
 * @class
 * @name edu.kit.informatik.studyplan.client.model.user.SessionInformation
 * @extends {edu.kit.informatik.studyplan.client.model.system.CookieModel}
 * Singleton which represents all relevant information of the current session
 */

edu.kit.informatik.studyplan.client.model.user.SessionInformation = (function () {
    /**
     * @this {edu.kit.informatik.studyplan.client.model.user.SessionInformation}
     */
    "use strict";
    /**
     * @type {edu.kit.informatik.studyplan.client.model.user.SessionInformation}
     */
    var instance = null;
    /**
     * @constructor
     * @name edu.kit.informatik.studyplan.client.model.user.SessionInformation
     */
    var Constructor = edu.kit.informatik.studyplan.client.model.system.CookieModel.extend({
        // cookie storage name
        url: "edu.kit.informatik.studyplan.client.model.user.SessionInformation.storage",
        initialize: function () {
            this.set('student', new edu.kit.informatik.studyplan.client.model.user.Student())
        },
        /**
         * Method which sets a random value for state
         */
        generateState: function () {
            var random = null;
            // Use crypto API if available
            if (window.crypto.getRandomValues) {
                var array = new Uint32Array(20),
                    randomNumber = 1;
                window.crypto.getRandomValues(array);
                var i;
                for (i = 0; i < array.length; i++) {
                    randomNumber *= array[i];
                }
                random = randomNumber.toString(36);
            } else {
                random = Math.ceil(Math.random() * 10000000 + 1000000).toString(36);
            }
            this.set('state', random.substr(0, 30));
            this.save();
        },
        /**
         * Method for retrieval of the Login URL
         */
        getLoginUrl: function () {
            return API_DOMAIN + "/auth/login?response_type=token&client_id=" + API_KEY + "&scope=student&state=" + this.get('state');
        },
        /**
         * Method which checks if the user is logged in
         */
        isLoggedIn: function () {
            if (edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().has('access_token')) {
                var accessToken = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('access_token');
                if (accessToken !== null && accessToken != undefined) {
                    return true;
                }
            }
            return false;
        },
        toJSON: function () {
            return {
                access_token: this.get('access_token'),
                expires_in: this.get('expires_in'),
                state: this.get('state')
            }
        }
    });
    return {
        /**
         * @return {edu.kit.informatik.studyplan.client.model.user.SessionInformation}
         */
        getInstance: function () {
            if (instance === null) {
                instance = new Constructor();
            }
            if (!instance.get('wasLoaded')) {
                instance.fetch({
                    success: function () {
                        if (!instance.has('state')) {
                            console.log("edu.kit.informatik.studyplan.client.model.user.SessionInformation");
                            console.log(instance.get('state'));
                            instance.generateState();
                            instance.save();
                        }
                        instance.set('wasLoaded', true);
                    }
                });
            }
            return instance;
        }
    };
}());