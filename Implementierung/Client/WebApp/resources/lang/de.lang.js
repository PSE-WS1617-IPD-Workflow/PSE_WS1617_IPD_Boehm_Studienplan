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