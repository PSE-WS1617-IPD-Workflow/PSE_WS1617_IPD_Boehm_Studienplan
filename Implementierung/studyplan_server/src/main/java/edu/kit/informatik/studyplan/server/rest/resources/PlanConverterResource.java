package edu.kit.informatik.studyplan.server.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Diese Klasse repräsentiert die Plankonverter-Ressource.
 */
@Path("plans")
public class PlanConverterResource {
	/**
	 * Erstellt eine PlanKonverter-Ressource.
	 */
	public PlanConverterResource() {

	}

	/**
	 * GET-Anfrage: Gibt die PDF-Version des Plans mit den gegebenen ID zurück.
	 * 
	 * @param planID
	 *            ID des zu konvertierenden Plans.
	 * @param accessToken
	 *            Ein Token, zur Authentifizierung der Klient.
	 * @return die PDF-Version des Plans.
	 */
	@Produces("application/pdf")
	@GET
	@Path("/{planId}/pdf")
	public Response convertPlanToPDF(@PathParam(value = "planId") String planID,
			@QueryParam("acess_token") String accessToken) {
		// TODO: implement
		return null;
	}
}
