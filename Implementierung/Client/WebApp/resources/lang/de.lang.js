var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
LM.messages = LM.messages || {};
LM.messages["de"] = {};
LM.messages["de"]["test"] = "Es funktioniert";
LM.messages["de"]["error.noUrl"] = "FEHLER: Es wurde keine URL spezifiziert";
LM.messages["de"]["error.invalidMethod"] = "FEHLER: Es wurde keine gültige Sync-Methode ausgewählt";
// HEADER
LM.messages["de"]["icon"] = "<big>STUDY</big>plan";
LM.messages["de"]["loggedIn"] = "Angemeldet";
LM.messages["de"]["notLoggedIn"] = "Nicht angemeldet";
LM.messages["de"]["profile"] = "Profil/Bestandene Module";
LM.messages["de"]["logout"] = "Logout";
LM.messages["de"]["login"] = "Login";
// Login
LM.messages["de"]["welcome"] = "Herzlich willkommen!"
LM.messages["de"]["welcomeText"] = "Herzlich willkommen bei STUDYplan. Zur Benutzung der Software loggen Sie sich bitte ein."
// Sync
LM.messages["de"]["connectionErrorTitle"] = "Fehler";
LM.messages["de"]["connectionErrorText-400"] = "Es ist ein Fehler aufgetreten. Bitte kontaktieren Sie den Administrator. (Statuscode 400)";
LM.messages["de"]["connectionErrorText-401"] = "Bitte loggen Sie sich erneut ein!";
LM.messages["de"]["connectionErrorText-404"] = "Die Ressource, auf welche Sie zugreifen wollten, existiert nicht. (Statuscode 404)";
LM.messages["de"]["connectionErrorText-405"] = "Es ist ein Fehler aufgetreten. Bitte kontaktieren Sie den Administrator. (Statuscode 405)";
LM.messages["de"]["connectionErrorText-422"] = "Ihre Anfrage konnte nicht bearbeitet werden, da sie keinen Sinn ergibt. Wenn Sie sich jetzt wundern, warum das passiert ist, kontaktieren Sie bitte den Administrator. (Statuscode 422)";
LM.messages["de"]["connectionErrorText-500"] = "Es ist ein Server-Fehler aufgetreten. Bitte kontaktieren Sie den Administrator oder versuchen Sie es später erneut. (Statuscode 500)";
LM.messages["de"]["connectionErrorText-timeout"] = "Die Verbindung zum Server ist fehlgeschlagen. Bitte versuchen Sie es später erneut."
LM.messages["de"]["connectionErrorText-abort"] = "Die Verbindung zum Server ist fehlgeschlagen. Bitte versuchen Sie es später erneut."
// Main Page
LM.messages["de"]["planNameQuestion"]="Wie soll der neue Plan heißen?";
// Plan List
LM.messages["de"]["execute"]="ausführen";
LM.messages["de"]["comparePlan"]="vergleichen";
LM.messages["de"]["selectAction"]="Aktion auswählen";
LM.messages["de"]["planName"]="Name";
LM.messages["de"]["planEctsSum"]="ECTS-Summe";
LM.messages["de"]["only2PlansComparableTitle"]="Vergleich nicht möglich";
LM.messages["de"]["only2PlansComparableText"]="Es können nur zwei Pläne gleichzeitig verglichen werden.";
// Plan List Element
LM.messages["de"]["deleteSelectedPlansButton"] = "Makierte Pläne Löschen";
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
LM.messages["de"]["changePlanNameIcon"] = "Plannamen bearbeiten";
LM.messages["de"]["makeProposal"] = "Plan vervollständigen";
LM.messages["de"]["verify"] = "Überprüfen";
LM.messages["de"]["verificationSuccessTitle"] = "Verifikation erfolgreich!";
LM.messages["de"]["verificationSuccessText"] = "Der Studienplan wurde erfolgreich überprüft und ist korrekt.";
LM.messages["de"]["verificationFailTitle"] = "Verifikation fehlgeschlagen!";
LM.messages["de"]["verificationFailText"] = "Der Studienplan wurde überprüft und ist nicht korrekt. Module, bei denen Voraussetzungen nicht erfüllt sind, wurden rot markiert.";
LM.messages["de"]["verificationFieldDescription"] = "Sie haben in folgenden Bereichen die ECTS-Mindestzahl unterschritten: ";
LM.messages["de"]["verificationGroupDescription"] = "Sie haben in folgenden Bereichen zu viele/zu wenig Module ausgewählt: ";
LM.messages["de"]["verificationCompulsoryDescription"] = "Sie müssen folgende Module zwingend hören: ";
LM.messages["de"]["OK"] = "OK";
LM.messages["de"]["changeSavedTitle"] = "Änderung gespeichert";
LM.messages["de"]["changeSavedText"] = "Die Änderung wurde erfolgreich gespeichert.";
LM.messages["de"]["nameTooLongTitle"] = "Name zu lang";
LM.messages["de"]["nameTooLongText"] = "Der Name darf höchstens 100 Zeichen lang sein.";


LM.messages["de"]["invalidStateTitle"] = "Authentifizierungsfehler";
LM.messages["de"]["invalidStateText"] = "Bitte versuchen Sie die Anmeldung erneut.";
LM.messages["de"]["authErrorinvalid_requestTitle"] = "Authentifizierungsfehler";
LM.messages["de"]["authErrorinvalid_requestText"] = "Es ist ein Systemfehler aufgetreten. Nitte kontaktieren Sie den Administrator.";
LM.messages["de"]["authErrorunsupported_response_typeTitle"] = "Authentifizierungsfehler";
LM.messages["de"]["authErrorunsupported_response_typeText"] = "Es ist ein Systemfehler aufgetreten. Nitte kontaktieren Sie den Administrator.";
LM.messages["de"]["authErrorinvalid_scopeTitle"] = "Authentifizierungsfehler";
LM.messages["de"]["authErrorinvalid_scopeText"] = "Es ist ein Systemfehler aufgetreten. Bitte kontaktieren Sie den Administrator.";
LM.messages["de"]["authErrorserver_errorTitle"] = "Authentifizierungsfehler";
LM.messages["de"]["authErrorserver_errorText"] = "Es ist ein Systemfehler aufgetreten. Bitte kontaktieren Sie den Administrator.";
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
LM.messages["de"]["generationExplanation1"] = "Wir benötigen einige Hinweise über Ihre Studienziele, um Ihnen einen Studienplan vorschlagen zu können.";
LM.messages["de"]["generationExplanation2"] = "Sie können mit dem Pfeil nach oben und dem Pfeil nach unten bewerten, welche Veranstaltungen Ihnen gut, und welche Ihnen eher schlecht gefallen.";
LM.messages["de"]["generationExplanation3"] = "Hier können Sie noch weitere Einschränkungen für Ihren Studienplan machen.";
LM.messages["de"]["generationEctsQuestion"] = "Wie viele ECTS-Punkte möchten Sie mindestestens und maximal pro Semester erreichen?";
LM.messages["de"]["generationSemesterQuestion"] = "Wie viele Semester möchten Sie mindestens und maximal studieren?";
LM.messages["de"]["generationChooseFunction"] = "Studienziel:";
LM.messages["de"]["GET-plans-proposal-connectionErrorText-400"] = "Für diese Einstellungen konnte kein Plan erstellt werden";


//RadioSlider
LM.messages["de"]["radioSliderMin"] = "min";
LM.messages["de"]["radioSliderMax"] = "max";



//Plan
LM.messages["de"]["notInsertTwiceTitle"] = "Modul bereits vorhanden";
LM.messages["de"]["notInsertTwiceText"] = "Ein Modul kann nur einmal hinzugefügt werden. Dieses wurde bereits hinzugefügt.";
LM.messages["de"]["wrongSemesterTypeTitle"] = "Falsches Semester";
LM.messages["de"]["wrongSemesterTypeText-ST"] = "Sie können dieses Modul nicht im Sommersemester hören";
LM.messages["de"]["wrongSemesterTypeText-WT"] = "Sie können dieses Modul nicht im Wintersemester hören";

LM.messages["de"]["sum"] = "Gesamt: ";
LM.messages["de"]["modules"] = "Module";
//SignupWizard
LM.messages["de"]["signUpTitle1"] = "Herzlich Willkommen!";
LM.messages["de"]["signUpExplanation1"] = "Wir benötigen einige Informationen über Sie, um Ihnen bei der Studienplanerstellung helfen zu können. Diese können Sie auf den folgenden Seiten angeben.";
LM.messages["de"]["signUpExplanation2"] = "";
LM.messages["de"]["signUpTitle2"] = "Beendete Module";
LM.messages["de"]["signUpExplanation2"] = "Bitte geben Sie an, welche Module Sie bereits bestanden haben.";
LM.messages["de"]["discipline"] = "Studienfach:";
LM.messages["de"]["beginningdate"] = "Studienbeginn:";
LM.messages["de"]["winterterm"] = "Wintersemester ";
LM.messages["de"]["summerterm"] = "Sommersemester ";
 
// Profile
LM.messages["de"]["savePlan"] = "Speichern";
LM.messages["de"]["profileSavedTitle"] = "Profil gespeichert";
LM.messages["de"]["profileSavedText"] = "Das Profil wurde erfolgreich gespeichert.";


LM.messages["de"]["applyFilterButton"] = "Filter anwenden";
LM.messages["de"]["mainMenuButton"] = "Hauptmenü";

LM.messages["de"]["deleteUser"] = "Konto löschen";
LM.messages["de"]["deleteUserPrompt"] = "Profil wirklich löschen?";
LM.messages["de"]["deleteUserSuccess"] = "Erfolgreich! Thank you for traveling with StudyPlan!";

// Not Found
LM.messages["de"]["notFoundTitle"] = "Diese Seite existiert nicht.";

// Proposed plan
LM.messages["de"]["newNameRequest"] = "Wie soll der neue Plan heißen?";
LM.messages["de"]["deleteProposal"] = "Plan verwerfen";
LM.messages["de"]["saveProposal"] = "Plan ersetzen";
LM.messages["de"]["saveAsProposal"] = "Unter anderem Namen speichern";
LM.messages["de"]["proposal"] = "Vorschlag";
LM.messages["de"]["proposalSidebartitle"] = "Studienplan-Vorschlag";
LM.messages["de"]["proposalSidebarExplanation"] = "Dies ist der auf Basis Ihrer Interessen generierte Studienplan.";
LM.messages["de"]["proposalSidebarAction"] = "Aktionen";

// Internet connection
LM.messages["de"]["connectionBackTitle"] = "Verbindung wiederhergestellt";
LM.messages["de"]["connectionBackText"] = "Sie können nun weiterarbeiten.";
LM.messages["de"]["connectionLostTitle"] = "Verbindung unterbrochen";
LM.messages["de"]["connectionLostText"] = "Sie können aktuell keine Daten speichern.";

// Search module
LM.messages["de"]["allValues"] = "Alles";

// Module Info Sidebar
LM.messages["de"]["lecturer"] = "Dozent:";
LM.messages["de"]["ECTS"] = "ECTS:";
LM.messages["de"]["description"] = "Beschreibung"
LM.messages["de"]["constraints"] = "Bedingungen";
LM.messages["de"][""] = "";

// Module Constraint types
LM.messages["de"]["constraintType_prerequisite"] = "Voraussetzungen";
LM.messages["de"]["constraintType_overlapping"] = "Überlappungen";
LM.messages["de"]["constraintType_semester_link"] = "Zusammengehörend (im selben Semester)";
LM.messages["de"]["constraintType_plan_link"] = "Zusammengehörend";


/**
 *  Detailed Error Strings
 ************************************
 * Format:
 * [METHOD]-[MODEL ERROR KEY]-connectionErrorText-[HTTP STATUS CODE]
 *
 */

// fields (fields)
LM.messages["de"]["GET-fields-connectionErrorText-422"] = "Es ist ein interner Fehler aufgetreten. Wir können keine Bereiche für Sie laden, da wir Ihre Fachrichtung nicht kennen. (Statuscode 422)";


// filters (filters)
LM.messages["de"]["GET-filters-connectionErrorText-422"] = "Es ist ein interner Fehler aufgetreten. Wir können keine Filter für Sie laden, da wir Ihre Fachrichtung nicht kennen. (Statuscode 422)";


// modules collection (modules)
LM.messages["de"]["GET-modules-connectionErrorText-422"] = "Es ist ein interner Fehler aufgetreten. Wir können keine Modulliste für Sie laden, da wir Ihre Fachrichtung nicht kennen. (Statuscode 422)";

// modules (modules-id)
LM.messages["de"]["GET-modules-id-connectionErrorText-404"] = "Das gewünschte Modul konnte nicht gefunden werden. (Statuscode 404)";


// plans collection (plans)
LM.messages["de"]["POST-plans-connectionErrorText-422"] = "Ein Plan dieses Namens existiert bereits. (Statuscode 422)";

// plans (plans-id)
LM.messages["de"]["POST-plans-id-connectionErrorText-400"] = "Bitte geben Sie Ihrem Studienplan einen (nicht leeren) Namen!";
LM.messages["de"]["PATCH-plans-id-connectionErrorText-400"] = "Bitte geben Sie Ihrem Studienplan einen (nicht leeren) Namen!";
LM.messages["de"]["PUT-plans-id-connectionErrorText-404"] = "Es ist ein interner Fehler aufgetreten. Der zu ersetzende Plan konnte nicht gefunden werden. (Statuscode 404)";
LM.messages["de"]["PATCH-plans-id-connectionErrorText-404"] = "Der Plan, der umbenannt werden soll, konnte nicht gefunden werden. (Statuscode 404)";
LM.messages["de"]["DELETE-plans-id-connectionErrorText-404"] = "Der Plan, der gelöscht werden soll, konnte nicht gefunden werden. (Statuscode 404)";
LM.messages["de"]["GET-plans-id-connectionErrorText-404"] = "Der Plan konnte nicht gefunden werden. (Statuscode 404)";
LM.messages["de"]["POST-plans-id-connectionErrorText-422"] = "Ein Plan mit diesem Namen existiert bereits. (Statuscode 422)";

// plans verification (plans-verification)
LM.messages["de"]["GET-plans-verification-connectionErrorText-404"] = "Der Plan, der überprüft werden soll, konnte nicht gefunden werden. (Statuscode 404)";

// plans proposal (plans-proposal)
LM.messages["de"]["GET-plans-proposal-connectionErrorText-404"] = "Der zu vervollständigende Plan oder das gewählte Studienziel konnte nicht gefunden werden. (Statuscode 404)";


// plans modules collection (plans-modules)
LM.messages["de"]["GET-plans-modules-connectionErrorText-422"] = "Es ist ein interner Fehler aufgetreten. Wir können keine Modulliste für Sie laden, da wir Ihre Fachrichtung nicht kennen. (Statuscode 422)";
LM.messages["de"]["GET-plans-modules-connectionErrorText-404"] = "Es ist ein interner Fehler aufgetreten. Wir können keine Modulliste für Sie laden, da der Plan, zu dem die Modulpräferenzen geladen werden sollen, nicht gefunden werden konnte. (Statuscode 404)";

// plans modules (plans-modules-id)
LM.messages["de"]["deletePlanPrompt"] = "Den Plan wirklich löschen?";
LM.messages["de"]["deletePlansPrompt"] = "Die Pläne wirklich löschen?";
LM.messages["de"]["DELETE-plans-modules-id-connectionErrorText-422"] = "Es ist ein interner Fehler aufgetreten. Das Modul, das aus dem Plan entfernt werden soll, war nicht im Plan vorhanden. (Statuscode 422)";
LM.messages["de"]["GET-plans-modules-id-connectionErrorText-404"] = "Das gewünschte Modul oder der Plan, zu welchem die zugehörigen Modulpräferenzen gehören, konnte nicht gefunden werden. (Statuscode 404)";
LM.messages["de"]["PUT-plans-modules-id-connectionErrorText-400"] = "Das Modul darf in diesem Zyklus nicht verwendet werden (es gibt Module die nur im Winter- bzw. nur im Sommersemester gehört werden dürfen)";
LM.messages["de"]["PUT-plans-modules-id-connectionErrorText-404"] = "Der Plan oder das Modul, das innerhalb des Plans platziert werden soll, konnte nicht gefunden werden. (Statuscode 404)";
LM.messages["de"]["DELETE-plans-modules-id-connectionErrorText-404"] = "Der Plan oder das Modul, das aus dem Plan entfernt werden soll, konnte nicht gefunden werden. (Statuscode 404)";

// modules preference (plans-modules-preference)
LM.messages["de"]["PUT-plans-modules-preference-connectionErrorText-422"] = "Es ist ein interner Fehler aufgetreten. Das Modul, dessen Präferenz entfernt werden soll, hatte vorher keine Präferenz. (Statuscode 422)";
LM.messages["de"]["PUT-plans-modules-preference-connectionErrorText-404"] = "Es ist ein interner Fehler aufgetreten. Die Modul-Präferenz konnte nicht gespeichert werden, da das Modul oder der Plan, der gerade bearbeitet wird, nicht gefunden werden konnten. (Statuscode 404)";

// student (student)
LM.messages["de"]["PUT-student-connectionErrorText-404"] = "Die gewählte Fachrichtung konnte nicht gefunden werden. (Statuscode 404)";


// Nicht notwendig, da direkt von Server abgerufen: LM.messages["de"]["GET-plans-id-pdf-404"] = "Der Plan, der exportiert werden soll, konnte nicht gefunden werden. (Statuscode 404)";

// Nicht notwendig, da direkt von Server abgerufen: LM.messages["de"]["GET-plans-id-pdf-503"] = "Es ist ein interner Fehler aufgetreten. Der Exportdienst ist zurzeit außer Betrieb. Bitte kontaktieren Sie den Administrator. (Statuscode 503)";

//WT/ST
LM.messages["de"]["semesterTypeName-WT"] = "Wintersemester";
LM.messages["de"]["semesterTypeName-ST"] = "Sommersemester";

// Plan
LM.messages["de"]["semesterLimit"] = "Leider ist es nicht möglich länger als 200 Semester zu studieren - aber Glückwunsch dazu überhaupt soweit gekommen zu sein!"

// Main Page
LM.messages["de"]["mainPageText"] = "Auf dieser Seite finden Sie Ihre Studienpläne.<br>Durch Klick auf den Button in der rechten unteren Ecke können Sie neue Studienpläne erstellen. Unter \"Profil\" können Sie weitere bestandene Module hinzufügen.";


/**
 *  Tour Texts
 ************************************
 * Format:
 * TOUR-[TOUR].[MESSAGE KEY]
 *
 */
LM.messages["de"]["noHelpAvailableTitle"]="Keine Hilfe verfügbar";
LM.messages["de"]["noHelpAvailableText"] = "Für diese Seite ist leider aktuell keine Hilfe verfügbar."
LM.messages["de"]["TOUR-next"] = "Weiter";
LM.messages["de"]["TOUR-done"] ="Fertig";
//LM.messages["de"]["TOUR-."] = "";
//LM.messages["de"]["TOUR-MainPage."] = "";
LM.messages["de"]["TOUR-MainPage.welcome"] = "Herzlich willkommen, dies ist die Hauptseite der Anwendung, mit welcher Sie Ihre Pläne verwalten können.";
LM.messages["de"]["TOUR-MainPage.table"] = "Hier sehen Sie Ihre erstellten Studienpläne und können Sie bearbeiten, duplizieren, löschen und exportieren.";
LM.messages["de"]["TOUR-MainPage.mainPageAddPlan"] = "Hier können Sie neue Pläne hinzufügen.";