package edu.kit.informatik.studyplan.server.rest;

import org.stuff.PDF;

/**
 * Diese Klasse repräsentiert die Plankonverter-Ressource.
 */
public class PlanConverterResource {
	/**
	 * Erstellt eine PlanKonverter-Ressource.
	 */
	public PlanConverterResource(){
		
	}
	/**
	 * GET-Anfrage:
	 * Gibt die PDF-Version des Plans mit den gegebenen ID zurück.
	 * @param planID ID des zu konvertierenden Plans.
	 * @param accessToken Ein Token, zur Authentifizierung der Klient.
	 * @return die PDF-Version des Plans.
	 */
	public PDF convertplanToPDF(String planID, String accessToken){
		return null;
	}
}
