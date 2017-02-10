package edu.kit.informatik.studyplan.server.rest.resources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.ModulePreference;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.VerificationState;
import edu.kit.informatik.studyplan.server.model.userdata.dao.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.dao.PlanDao;
import edu.kit.informatik.studyplan.server.model.userdata.dao.PlanDaoFactory;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;
import edu.kit.informatik.studyplan.server.rest.resources.json.JsonModule;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

/**
 * Diese Klasse repräsentiert die Pläne-Ressource.
 */
@Path("/plans")
@AuthorizationNeeded
public class PlansResource {
	@Inject
	Provider<AuthorizationContext> contextProvider;

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
				|| planInput.getPlan().getCreditPoints() != 0) {
			throw new BadRequestException();
		}
		String newId = PlanDaoFactory.getPlanDao(contextProvider.get()).updatePlan(planInput.getPlan());
		if (newId == null) {
			throw new UnprocessableEntityException();
		}
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
		List<Plan> result = contextProvider.get().getUser().getPlans().stream()
				.map(plan -> {
					plan.setModuleEntries(new ArrayList<ModuleEntry>());
					plan.setModulePreferences(new ArrayList<ModulePreference>());
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
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PlanInOut replacePlan(@PathParam("id") String planID, PlanInOut planInput) {
		if (planInput.getPlan().getModuleEntries() == null || planInput.getPlan().getPreferences() == null
				|| planInput.getPlan().getVerificationState() == null || planInput.getPlan().getName() == null
				|| !Objects.equals(planInput.getPlan().getIdentifier(), planID)) {
			throw new BadRequestException();
		}
		Plan plan = PlanDaoFactory.getPlanDao(contextProvider.get()).getPlanById(planInput.getPlan().getIdentifier());
		if (plan == null) {
			throw new NotFoundException();
		}
		planInput.getPlan().setVerificationState(VerificationState.NOT_VERIFIED);
		PlanDaoFactory.getPlanDao(contextProvider.get()).updatePlan(planInput.getPlan());
		return planInput;
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
	@Produces(MediaType.APPLICATION_JSON)
	public PlanInOut getPlan(@PathParam("plan-id") String planId) {
		Plan result = PlanDaoFactory.getPlanDao(contextProvider.get()).getPlanById(planId);
		if (result == null) {
			throw new NotFoundException();
		} else {
			return new PlanInOut(result);
		}
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
	@PATCH
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PlanInOut renamePlan(@PathParam("id") String planId, PlanInOut planInput) {
		if (!Objects.equals(planInput.getPlan().getIdentifier(), planId)
				|| planInput.getPlan().getVerificationState() != null
				|| planInput.getPlan().getModuleEntries() != null || planInput.getPlan().getPreferences() != null
				|| planInput.getPlan().getCreditPoints() != 0) {
			throw new BadRequestException();
		}
		if (contextProvider.get().getUser().getPlans().stream()
				.anyMatch(plan -> plan.getName().equals(planInput.getPlan().getName()))) {
			throw new UnprocessableEntityException();
		}
		Plan plan = PlanDaoFactory.getPlanDao(contextProvider.get()).getPlanById(planId);
		if (plan == null) {
			throw new NotFoundException();
		}
		plan.setName(planInput.getPlan().getName());
		PlanDaoFactory.getPlanDao(contextProvider.get()).updatePlan(plan);
		planInput.getPlan().setIdentifier(planId);
		return planInput;
	}

	/**
	 * DELETE-Anfrage: Löscht den Plan mit dem gegebenen ID.
	 * 
	 * @param planId
	 *            ID des zu löschenden Plans.
	 */
	@DELETE
	@Path("/{id}")
	public Response deletePlan(@PathParam("id") String planId) {
		PlanDao dao = PlanDaoFactory.getPlanDao(contextProvider.get());
		Plan plan = dao.getPlanById(planId);
		if (plan == null) {
			throw new UnprocessableEntityException();
		}
		dao.deletePlan(plan);
		dao.cleanUp();
		return Response.ok().build();
	}

	/* ******************************  /{id}/modules  ******************************** */

	/**
	 * GET Anfrage: Gibt die Liste der JSON-Representationen von Modulen, die :
	 * -in dem Plan mit der gegebenen ID sind und -den gegebenen Filtern
	 * entsprechen, zurück.
	 *
	 * @param planID
	 *            ID des zu bearbeitenden Plans.
	 * @param jsonFilter
	 *            die benutzeten Filtern als Get-Parameter.
	 * @return eine Liste der JSON-Representationen von Modulen.
	 */
	@GET
	@Path("/{id}/modules")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List<JsonModule>> getModules(@PathParam("id") String planId, @Context UriInfo uriInfo) {
		User user = contextProvider.get().getUser();
		if (user.getDiscipline() == null) {
			throw new UnprocessableEntityException();
		}
		Plan plan = PlanDaoFactory.getPlanDao(contextProvider.get()).getPlanById(planId);
		if (plan == null) {
			throw new NotFoundException();
		}
		Filter filter = PlanModulesResource.getFilterFromRequest(uriInfo.getQueryParameters(), user.getDiscipline());
		if (filter == null) {
			throw new BadRequestException();
		}
		List<JsonModule> result = ModuleDaoFactory.getModuleDao()
				.getModulesByFilter(filter, user.getDiscipline())
				.parallelStream().map(m -> {
					JsonModule newModule = new JsonModule();
					newModule.setId(m.getIdentifier());
					newModule.setName(m.getName());
					newModule.setCreditPoints(m.getCreditPoints());
					newModule.setLecturer(m.getModuleDescription().getLecturer());
					newModule.setCycleType(m.getCycleType());
					newModule.setPreference(plan.getPreferenceForModule(m));
					return newModule;
				})
				.collect(Collectors.toList());
		return SimpleJsonResponse.build("modules", result);
	}

	/**
	 * GET Anfrage: Gibt eine JSON-Representation von dem Modul mit der
	 * gegebenen ID, der in dem Plan mit der gegebenen ID ist, zurück.
	 *
	 * @param planID
	 *            ID des zu bearbeitenden Plans.
	 * @param moduleID
	 *            ID des erforderten Modul.
	 * @return eine JSON-Representation von dem Modul als JSON Objekt.
	 */
	@GET
	@Path("/{plan}/modules/{module}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, JsonModule> getModule(@PathParam("plan") String planId, @PathParam("module") String moduleId) {
		Plan plan = PlanDaoFactory.getPlanDao(contextProvider.get()).getPlanById(planId);
		if (plan == null) {
			throw new NotFoundException();
		}
		Module module = ModuleDaoFactory.getModuleDao().getModuleById(moduleId);
		if (module == null) {
			throw new NotFoundException();
		}
		JsonModule result = JsonModule.fromModule(module, null, plan.getPreferenceForModule(module));
		return SimpleJsonResponse.build("module", result);
	}

	/**
	 * PUT Anfrage: fügt das Modul als JSON Objekt zur Plan mit den gegebenen
	 * ModulID bzw. PlanID hinzu.
	 *
	 * @param planId
	 *            ID des zu bearbeitenden Plans.
	 * @param moduleId
	 *            ID des hinzuzufügenden Modul.
	 * @param jsonPutModule
	 *            das Modul als Get-Parameter.
	 * @return JSON-Representation des Moduls als JSON Objekt.
	 */
	@PUT
	@Path("/{plan}/modules/{module}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleInOut putModuleSemester(@PathParam("plan") String planId, @PathParam("module") String moduleId,
										ModuleInOut moduleInput) {
		if (!Objects.equals(moduleInput.getModule().getId(), moduleId)
				|| moduleInput.getModule().getSemester() == null) {
			throw new BadRequestException();
		}
		Plan plan = PlanDaoFactory.getPlanDao(contextProvider.get()).getPlanById(planId);
		if (plan == null) {
			throw new NotFoundException();
		}
		Module module = ModuleDaoFactory.getModuleDao().getModuleById(moduleId);
		if (module == null) {
			throw new NotFoundException();
		}
		if (plan.getModuleEntries().stream().anyMatch(entry -> entry.getModule().equals(module))
				|| moduleInput.getModule().getSemester()
					< contextProvider.get().getUser().getStudyStart().getDistanceToCurrentSemester()) {
			throw new UnprocessableEntityException();
		}
		plan.getModuleEntries().add(new ModuleEntry(module, moduleInput.getModule().getSemester()));
		plan.setVerificationState(VerificationState.NOT_VERIFIED);
		PlanDaoFactory.getPlanDao(contextProvider.get()).updatePlan(plan);
		return moduleInput;
	}

	/**
	 * DELETE-Anfrage: entfernt das Modul von dem Plan mit den gegebenen ModulID
	 * bzw. PlanID.
	 *
	 * @param planId
	 *            ID des zu bearbeitenden Plans.
	 * @param moduleId
	 *            ID des zu entfernenden Modul.
	 */
	@DELETE
	@Path("/{plan}/modules/{module}")
	public Response removeModuleSemester(@PathParam("plan") String planId, @PathParam("module") String moduleId) {
		Plan plan = PlanDaoFactory.getPlanDao(contextProvider.get()).getPlanById(planId);
		if (plan == null) {
			throw new NotFoundException();
		}
		Module module = ModuleDaoFactory.getModuleDao().getModuleById(moduleId);
		if (module == null) {
			throw new NotFoundException();
		}
		ModuleEntry moduleEntry = plan.getModuleEntries().stream()
				.filter(entry -> entry.getModule().equals(module))
				.findFirst().orElseThrow(UnprocessableEntityException::new);
		plan.getModuleEntries().remove(moduleEntry);
		plan.setVerificationState(VerificationState.NOT_VERIFIED);
		PlanDaoFactory.getPlanDao(contextProvider.get()).updatePlan(plan);
		return Response.ok().build();
	}

	/**
	 * PUT-Anfrage: setzt eine Bewertung als JSON Objekt für den Modul mit der
	 * gegebenen ID, der im Plan mit der gegebenen ID.
	 *
	 * @param planId
	 *            ID des zu bearbeitenden Plans.
	 * @param moduleId
	 *            ID des zu bewertenden Modul.
	 * @param moduleInput
	 *            die zu setzenden Bewertung des Moduls als GetParameters.
	 * @return die gesetzten Bewertung des Moduls als JSON Objekt.
	 */
	@PUT
	@Path("/{plan}/modules/{module}/preference")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleInOut setModulePreference(@PathParam("plan") String planId, @PathParam("module") String moduleId,
										   ModuleInOut moduleInput) {
		if (!Objects.equals(moduleInput.getModule().getId(), moduleId)) {
			throw new BadRequestException();
		}
		Module module = ModuleDaoFactory.getModuleDao().getModuleById(moduleId);
		if (module == null) {
			throw new NotFoundException();
		}
		Plan plan = PlanDaoFactory.getPlanDao(contextProvider.get()).getPlanById(planId);
		if (plan == null) {
			throw new NotFoundException();
		}
		List<ModulePreference> preferences = plan.getPreferences();
		if (moduleInput.getModule().getPreference() == null) {
			if (preferences.stream().noneMatch(preference -> preference.getModule().equals(module))) {
				throw new UnprocessableEntityException();
			}
			preferences.removeIf(preference -> preference.getModule().equals(module));
		} else {
			if (preferences.stream().anyMatch(preference -> preference.getModule().equals(module))) {
				throw new UnprocessableEntityException();
			}
			preferences.add(new ModulePreference(module, moduleInput.getModule().getPreference()));
		}
		PlanDaoFactory.getPlanDao(contextProvider.get()).updatePlan(plan);
		return moduleInput;
	}


	static class ModuleInOut {
		@JsonProperty("module")
		@NotNull
		private JsonModule module;

		public ModuleInOut() { }

		public ModuleInOut(JsonModule module) {
			this.module = module;
		}

		public JsonModule getModule() {
			return module;
		}

		public void setModule(JsonModule module) {
			this.module = module;
		}
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

	@Target({ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	@HttpMethod("PATCH")
	@interface PATCH {
	}
};
