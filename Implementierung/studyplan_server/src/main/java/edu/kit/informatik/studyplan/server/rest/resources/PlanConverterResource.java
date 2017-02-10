package edu.kit.informatik.studyplan.server.rest.resources;

import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.dao.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.dao.PlanDaoFactory;

/**
 * Diese Klasse repräsentiert die Plankonverter-Ressource.
 */
@Path("stuff")
public class PlanConverterResource {
	
	private Provider<AuthorizationContext> contextProvider;

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
	@Produces("text/html")
	@GET
	@Path("/{planId}/pdf")
	public Response convertPlanToPDF(@PathParam(value = "planId") String planId,
			@QueryParam("acess_token") String accessToken) {
		Plan plan = PlanDaoFactory.getPlanDao(null).getPlanById(planId);
		PlanConverter converter = new PlanConverter(plan);
		converter.getWriter().flush();
		return Response.ok(converter.getWriter().toString()).build();
	}
}
