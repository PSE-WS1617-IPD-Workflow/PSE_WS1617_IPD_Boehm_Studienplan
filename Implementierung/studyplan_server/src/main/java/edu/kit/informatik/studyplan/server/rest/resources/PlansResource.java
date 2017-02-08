package edu.kit.informatik.studyplan.server.rest.resources;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.dao.PlanDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDaoFactory;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.GetParameters;
import edu.kit.informatik.studyplan.server.rest.JSONObject;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Diese Klasse repräsentiert die Pläne-Ressource.
 */
@Path("/plans")
@AuthorizationNeeded
public class PlansResource {
	@Inject
	AuthorizationContext context;

	/**
	 * POST-Anfrage: Erstellt einen neuen Studienplan.
	 * 
	 * @return jsonPlan der erstellte Plan als JSON Objekt.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PlanInOut createPlan(PlanInOut planInput) {
		if (planInput.getPlan().getIdentifier() != null || planInput.getPlan().getVerificationState() != null
				|| planInput.getPlan().getModuleEntries() != null || planInput.getPlan().getPreferences() != null
				|| planInput.getPlan().getCreditPoints() != 0)
			throw new BadRequestException();
		String newId = PlanDaoFactory.getPlanDao().updatePlan(planInput.getPlan());
		if (newId == null)
			throw new UnprocessableEntityException();
		planInput.getPlan().setIdentifier(newId);
		return planInput;
	}

	/**
	 * GET-Anfrage: Gibt eine Liste aller vorhandenen StudienPläne zurück.
	 * 
	 * @param jsonPlanList
	 *            einen Array aller vorhandenen StudienPläne als JSON Objekte.
	 * @return jsonPlanList eine Liste aller vorhandenen StudienPläne als JSON
	 *         Objekte.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List<Plan>> getPlans() {
		List<Plan> result = context.getUser().getPlans().stream()
				.map(plan -> {
					plan.setModuleEntries(null);
					plan.setModulePreferences(null);
					return plan;
				})
				.collect(Collectors.toList());
		return SimpleJsonResponse.build("plans", result);
	}

	/**
	 * PUT-Anfrage: Ersetzt den Plan mit der gegebenen ID mit den gegeben Plan .
	 * 
	 * @param planID
	 *            ID des zu entfernenden Plans.
	 * @param jsonPlan
	 *            der zu speichernden Plan als JSON Objekt.
	 * @return jsonNewPlan der gespeicherte Plan.
	 */
	public JSONObject replacePlan(String planID, JSONObject jsonPlan) {
		return null;
	}

	/**
	 * GET-Anfrage: Gibt den Plan mit der gegebenen ID zurück.
	 * 
	 * @param planID
	 *            ID des angefragten Plans.
	 * @return jsonPlan der Plan als JSON Objekt.
	 */
	@GET
	@Path("/{plan-id}")
	public PlanInOut getPlan(@PathParam("plan-id") String planId) {
		Plan result = PlanDaoFactory.getPlanDao().getPlanById(planId);
		if (result == null)
			throw new NotFoundException();
		else
			return new PlanInOut(result);
	}

	/**
	 * PATCH-Anfrage: Bearbeitet den Plan mit der gegebenen ID.
	 * 
	 * @param planId
	 *            ID des zu bearbeitenden Plans.
	 * @param jsonPlan
	 *            der Plan als Get-Parameter.
	 * @return jsonChangedPlan JSON Objekt des bearbeiteten Plans.
	 */
	@POST
	@Path("/{id}")
	public PlanInOut renamePlan(@QueryParam("id") String planId, PlanInOut planInput) {
		if (planInput.getPlan().getIdentifier() != null || planInput.getPlan().getVerificationState() != null
				|| planInput.getPlan().getModuleEntries() != null || planInput.getPlan().getPreferences() != null
				|| planInput.getPlan().getCreditPoints() != 0)
			throw new BadRequestException();
		Plan plan = PlanDaoFactory.getPlanDao().getPlanById(planId);
		if (plan == null)
			throw new NotFoundException();
		plan.setName(planInput.getPlan().getName());
		try {
			PlanDaoFactory.getPlanDao().updatePlan(plan);  //TODO Error handling myself?
			planInput.getPlan().setIdentifier(planId);
			return planInput;
		} catch (Exception ex) {
			throw new UnprocessableEntityException();
		}
	}

	/**
	 * DELETE-Anfrage: Löscht den Plan mit dem gegebenen ID.
	 * 
	 * @param planId
	 *            ID des zu löschenden Plans.
	 */
	@DELETE
	@Path("/{id}")
	public Response deletePlan(@QueryParam("id") String planId) {
		Plan plan = PlanDaoFactory.getPlanDao().getPlanById(planId);
		if (plan == null)
			throw new UnprocessableEntityException();
		PlanDaoFactory.getPlanDao().deletePlan(plan);
		return Response.ok().build();
	}

	static class PlanInOut {
		@JsonProperty("plan")
		@NotNull
		private Plan plan;

		public PlanInOut() { }

		public PlanInOut(Plan plan) {
			this.plan = plan;
		}

		public Plan getPlan() {
			return plan;
		}

		public void setPlan(Plan plan) {
			this.plan = plan;
		}
	}
};
