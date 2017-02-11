var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
LM.messages = LM.messages || {};
LM.messages["de"] = {};
LM.messages["de"]["test"] = "Es funktioniert";
LM.messages["de"]["error.noUrl"] = "FEHLER: Es wurde keine URL spezifiziert";
LM.messages["de"]["error.invalidMethod"] = "FEHLER: Es wurde keine gültige Sync Methode ausgewählt";
// HEADER
LM.messages["de"]["icon"] = "<big>STUDY</big>plan";
LM.messages["de"]["loggedIn"] = "Angemeldet";
LM.messages["de"]["notLoggedIn"] = "Nicht angemeldet";
LM.messages["de"]["profile"] = "Profil";
LM.messages["de"]["logout"] = "Logout";
LM.messages["de"]["login"] = "Login";
// Login
LM.messages["de"]["welcome"] = "Herzlich willkommen!"
LM.messages["de"]["welcomeText"] = "Herzlich willkommen bei STUDYplan. Zur Benutzung der Software loggen Sie sich bitte ein."
// Sync
LM.messages["de"]["connectionErrorTitle"] = "Verbindungsfehler";
LM.messages["de"]["connectionErrorText-400"] = "Es ist zu einem Fehler gekommen. Bitte kontaktieren Sie den Administrator. (Statuscode 400)";
LM.messages["de"]["connectionErrorText-401"] = "Bitte loggen Sie sich erneut ein!";
LM.messages["de"]["connectionErrorText-404"] = "Die Resource, auf welche Sie zugreifen wollten existiert nicht. (Statuscode 404)";
LM.messages["de"]["connectionErrorText-405"] = "Es ist zu einem Fehler gekommen. Bitte kontaktieren Sie den Administrator. (Statuscode 405)";
LM.messages["de"]["connectionErrorText-422"] = "Ihre Anfrage konnte nicht bearbeitet werden, da sie keinen Sinn ergibt. Wenn Sie sich jetzt wundern, warum das passiert ist, kontaktieren Sie bitte den Administrator. (Statuscode 422)";
LM.messages["de"]["connectionErrorText-500"] = "Es ist zu einem Server-Fehler gekommen. Bitte kontaktieren Sie den Administrator oder versuchen Sie es später erneut. (Statuscode 500)";
LM.messages["de"]["connectionErrorText-timeout"] = "Die Verbindung zum Server ist fehlgeschlagen. Bitte versuchen Sie es später erneut."
LM.messages["de"]["connectionErrorText-abort"] = "Die Verbindung zum Server ist fehlgeschlagen. Bitte versuchen Sie es später erneut."
// Main Page
LM.messages["de"]["planNameQuestion"]="Wie soll der neue Plan heißen?";
// Plan List
LM.messages["de"]["execute"]="ausführen";
LM.messages["de"]["comparePlan"]="vergleichen";
LM.messages["de"]["selectAction"]="Aktion auswählen";
LM.messages["de"]["planName"]="Name";
LM.messages["de"]["planEctsSum"]="ECTS Summe";
LM.messages["de"]["only2PlansComparableTitle"]="Fehler beim Vergleich";
LM.messages["de"]["only2PlansComparableText"]="Es können nur 2 Pläne gleichzeitig verglichen werden!";
// Plan List Element
LM.messages["de"]["showPlan"] = "anzeigen";
LM.messages["de"]["duplicatePlan"] = "duplizieren";
LM.messages["de"]["deletePlan"] = "löschen";
LM.messages["de"]["exportPlan"] = "exportieren";
LM.messages["de"]["planDuplicatedTitle"] = "Plan dupliziert.";
LM.messages["de"]["planDuplicatedText"] = "Der Plan wurde erfolgreich dupliziert.";
LM.messages["de"]["planDeletedTitle"] = "Plan gelöscht.";
LM.messages["de"]["planDeletedText"] = "Der Plan wurde erfolgreich gelöscht.";

// RegularHeadBar
LM.messages["de"]["home"] = "Home";
LM.messages["de"]["makeProposal"] = "Plan vervollständigen";
LM.messages["de"]["verify"] = "Überprüfen";
LM.messages["de"]["verificationSuccessTitle"] = "Verifikation erfolgreich!";
LM.messages["de"]["verificationSuccessText"] = "Der Studienplan wurde erfolgreich überprüft und ist korrekt!";
LM.messages["de"]["verificationFailTitle"] = "Verifikation fehlgeschlagen!";
LM.messages["de"]["verificationFailText"] = "Der Studienplan wurde überprüft und ist nicht korrekt!";
LM.messages["de"]["changeSavedTitle"] = "Änderung gespeichert";
LM.messages["de"]["changeSavedText"] = "Die Änderung wurde erfolgreich gespeichert";

LM.messages["de"]["invalidStateTitle"] = "Authentifizierungsfehler";
LM.messages["de"]["invalidStateText"] = "Bitte versuchen Sie die Anmeldung erneut!";
LM.messages["de"]["authErrorinvalid_requestTitle"] = "Authentifizierungsfehler";
LM.messages["de"]["authErrorinvalid_requestText"] = "Es ist zu einem Systemfehler gekommen. Nitte kontaktieren Sie den Administrator!";
LM.messages["de"]["authErrorunsupported_response_typeTitle"] = "Authentifizierungsfehler";
LM.messages["de"]["authErrorunsupported_response_typeText"] = "Es ist zu einem Systemfehler gekommen. Nitte kontaktieren Sie den Administrator!";
LM.messages["de"]["authErrorinvalid_scopeTitle"] = "Authentifizierungsfehler";
LM.messages["de"]["authErrorinvalid_scopeText"] = "Es ist zu einem Systemfehler gekommen. Nitte kontaktieren Sie den Administrator!";
LM.messages["de"]["authErrorserver_errorTitle"] = "Authentifizierungsfehler";
LM.messages["de"]["authErrorserver_errorText"] = "Es ist zu einem Systemfehler gekommen. Nitte kontaktieren Sie den Administrator!";
LM.messages["de"]["authEndTitle"] = "ACHTUNG: Ende der Session!";
LM.messages["de"]["authEndText"] = "Bitte beachten Sie, dass Sie in weniger als 60 Sekunden ausgeloggt werden!";
LM.messages["de"]["realAuthEndTitle"] = "Sie wurden ausgeloggt";
LM.messages["de"]["realAuthEndText"] = "Bitte loggen Sie sich erneut ein.";
LM.messages["de"]["logoutSuccessfulTitle"] = "Logout erfolgreich";
LM.messages["de"]["logoutSuccessfulText"] = "";
LM.messages["de"]["loginSuccessfulTitle"] = "Login erfolgreich";
LM.messages["de"]["loginSuccessfulText"] = "";
//GenerationWizard
LM.messages["de"]["generationTitle"] = "Studienplan-Vorschlag erstellen";
LM.messages["de"]["generationExplanation1"] = "Wir benötigen einige Hinweise über deine Studienziele, um dir einen Studienplan vorschlagen zu können.";
LM.messages["de"]["generationExplanation2"] = "Du kannst mit dem Pfeil nach oben und dem Pfeil nach unten bewerten, welche Veranstaltungen dir gut, und welche eher schlecht gefallen.";
LM.messages["de"]["generationExplanation3"] = "Hier kannst du noch weitere Einschränkungen für deinen Studienplan machen.";
LM.messages["de"]["generationEctsQuestion"] = "Wie viele ECTS-Punkte möchtest du mindestestens und maximal pro Semester erreichen?";
LM.messages["de"]["generationSemesterQuestion"] = "Wie viele Semester möchtest du mindestens und maximal studieren?";
LM.messages["de"]["generationChooseFunction"] = "Studienziel:";
//ProfilePage
LM.messages["de"]["applyFilterButton"] = "Filter anwenden";
LM.messages["de"]["mainMenuButton"] = "Hauptmenü";

//RadioSlider
LM.messages["de"]["radioSliderMin"] = "min";
LM.messages["de"]["radioSliderMax"] = "max";

//Plan
LM.messages["de"]["notInsertTwiceTitle"] = "Module bereits vorhanden";
LM.messages["de"]["notInsertTwiceText"] = "Module können nur einmal dem Studienplan hinzugefügt werden. Dieses ist bereits im Plan vorhanden!";

LM.messages["de"]["sum"] = "Gesamt: ";
LM.messages["de"]["modules"] = "Module";
//SignupWizard
LM.messages["de"]["signUpTitle1"] = "Herzlich Willkommen!";
LM.messages["de"]["signUpExplanation1"] = "Wir benötigen einige Informationen über dich, um dir bei der Studienplanerstellung helfen zu können. Diese kannst du auf den folgenden Seiten angeben.";
LM.messages["de"]["signUpExplanation2"] = "";
LM.messages["de"]["signUpTitle2"] = "Beendete Module";
LM.messages["de"]["signUpExplanation2"] = "Bitte gib an, welche Module du bereits bestanden hast.";
LM.messages["de"]["discipline"] = "Studienfach:";
LM.messages["de"]["beginningdate"] = "Studienbeginn:";

// Profile
LM.messages["de"]["savePlan"] = "Speichern";
LM.messages["de"]["profileSavedTitle"] = "Profil gespeichert";
LM.messages["de"]["profileSavedText"] = "Das Profil wurde erfolgreich gespeichert.";

// Not Found
LM.messages["de"]["notFoundTitle"] = "Diese Seite existiert nicht!";
