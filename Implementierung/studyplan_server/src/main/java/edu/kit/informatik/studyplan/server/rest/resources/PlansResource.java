package edu.kit.informatik.studyplan.server.rest.resources;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.ModulePreference;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.Semester;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.VerificationState;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.dao.AbstractSecurityProvider;
import edu.kit.informatik.studyplan.server.model.userdata.dao.PlanDaoFactory;
import edu.kit.informatik.studyplan.server.pluginmanager.GenerationManager;
import edu.kit.informatik.studyplan.server.pluginmanager.VerificationManager;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;
import edu.kit.informatik.studyplan.server.rest.resources.json.JsonModule;
import edu.kit.informatik.studyplan.server.rest.resources.json.ModuleDto;
import edu.kit.informatik.studyplan.server.rest.resources.json.PlanDto;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;
import edu.kit.informatik.studyplan.server.verification.VerificationResult;

/**
 * REST resource for /plans.
 */
@Path("/plans")
public class PlansResource {
	
	/**
	 * maximum number of semesters a plan may contain
	 */
	public static final int MAX_SEMESTERS = 200;
	
	private static int maxPlanNameLength = 100;
	
	@Inject
	Provider<AuthorizationContext> context;

	private User getUser() {
		return context.get().getUser();
	}

	/**
	 * POST request handler. Creates a new Plan by a given Plan[name] object.
	 *
	 * @param planInput
	 *            the given plan.
	 * @return the created plan as Plan[id, name]
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public PlanInOut createPlan(PlanInOut planInput) {
		if (planInput == null
				|| planInput.getPlan() == null
				|| planInput.getPlan().getIdentifier() != null || planInput.getPlan().getName() == null
				|| planInput.getPlan().getName().trim().isEmpty()
				|| planInput.getPlan().getName().length() > maxPlanNameLength
				|| planInput.getPlan().getVerificationState() != null
				|| !planInput.getPlan().getModuleEntries().isEmpty()
				|| !planInput.getPlan().getPreferences().isEmpty()) {
			throw new BadRequestException();
		}
		if (getUser().getPlans().stream()
				.anyMatch(plan -> plan.getName().equals(planInput.getPlan().getName()))) {
			throw new UnprocessableEntityException();
		}
		planInput.getPlan().setUser(getUser());
		return Utils.withPlanDao(dao -> {
			Plan plan = planInput.getPlan();
			plan.getCreditPoints();
			plan.setVerificationState(VerificationState.NOT_VERIFIED);
			String newId = dao.updatePlan(plan);
			if (newId == null) {
				throw new UnprocessableEntityException();
			}
			planInput.getPlan().setIdentifier(newId);
			return planInput;
		});
	}

	/**
	 * GET request handler.
	 * 
	 * @return a list of all plans as JSON representation.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public Response getPlans() {
		List<PlanSummaryDto> result = getUser().getPlans().stream()
				.map(PlanSummaryDto::new)
				.collect(Collectors.toList());
		return Response.ok(SimpleJsonResponse.build("plans", result)).build();
	}
	
	
	/**
	 * DataTransferObject for a summary of plan, without the entries
	 * @author NiklasUhl
	 *
	 */
	public class PlanSummaryDto {
		@JsonProperty
		String id;
		@JsonProperty
		VerificationState status;
		@JsonProperty("creditpoints-sum")
		double creditPointsSum;
		@JsonProperty
		String name;
		
		/**
		 * creates an instance from the given plan
		 * @param plan the plan
		 */
		public PlanSummaryDto(Plan plan) {
			this.id = plan.getIdentifier();
			this.status = plan.getVerificationState();
			this.creditPointsSum = plan.getCreditPoints();
			this.name = plan.getName();
		}
	}

	/**
	 * PUT plans/{planId} request handler. Replaces a plan with a given JSON
	 * object. The plan is characterized as NOT_VERIFIED.
	 *
	 * @param planId
	 *            the old plan's id
	 * @param planInput
	 *            the new Plan object to replace the old one.
	 * @return the new plan
	 */
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public PlanInOut replacePlan(@PathParam("id") String planId, PlanInOut planInput) {
		if (planInput == null
				|| planInput.getPlan() == null 
				|| planInput.getPlan().getModuleEntries() == null || planInput.getPlan().getPreferences() == null
				|| planInput.getPlan().getName() == null
				|| planInput.getPlan().getName().trim().isEmpty()
				|| planInput.getPlan().getName().length() > maxPlanNameLength
				|| !Objects.equals(planInput.getPlan().getIdentifier(), planId)) {
			throw new BadRequestException();
		}
		if (planInput.getPlan().getModuleEntries().stream()
				.anyMatch(entry -> !isValid(entry, getUser().getStudyStart()))) {
			throw new BadRequestException("Invalid module entry.");
		}
		return Utils.withPlanDao(dao -> {
			Plan plan = dao.getPlanById(planId);
			if (plan == null || !getUser().equals(plan.getUser())) {
				throw new NotFoundException();
			}
			if (!plan.getName().equals(planInput.getPlan().getName()) 
					&& getUser().getPlans().stream()
						.anyMatch(entry -> entry.getName().equals(planInput.getPlan().getName()))) {
				throw new BadRequestException("Duplicate name.");
			}
			plan.setName(planInput.getPlan().getName());
			plan.getModuleEntries().clear();
			plan.getModuleEntries().addAll(planInput.getPlan().getModuleEntries());
			plan.setVerificationState(VerificationState.NOT_VERIFIED);
			dao.updatePlan(plan);
			return planInput;
		});
	}

	/**
	 * GET plans/{planId} request handler.
	 * 
	 * @param planId
	 *            the requested plan's id.
	 * @return JSON representation of the plan.
	 */
	@GET
	@Path("/{plan-id}")
	@Produces(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public Map<String, PlanDto> getPlan(@PathParam("plan-id") String planId) {
		return Utils.withPlanDao(dao -> {
			Plan result = dao.getPlanById(planId);
			if (result == null || !getUser().equals(result.getUser())) {
				throw new NotFoundException();
			} else {
				return SimpleJsonResponse.build("plan", new PlanDto(result));
			}
		});
	}

	/**
	 * PATCH plans/{planId} request. Renames the plan with the given id.
	 * 
	 * @param planId
	 *            the id of the plan to rename
	 * @param planInput
	 *            the plan object with the new name.
	 * @return the renamed plan
	 */
	@PATCH
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public PlanInOut renamePlan(@PathParam("id") String planId, PlanInOut planInput) {
		if (planInput == null
				|| planInput.getPlan() == null
				|| !Objects.equals(planInput.getPlan().getIdentifier(), planId)
				|| planInput.getPlan().getName() == null
				|| planInput.getPlan().getName().trim().isEmpty()
				|| planInput.getPlan().getName().length() > maxPlanNameLength
				|| planInput.getPlan().getVerificationState() != null
				|| !planInput.getPlan().getModuleEntries().isEmpty() || !planInput.getPlan().getPreferences().isEmpty()
				|| planInput.getPlan().getCreditPoints() != 0) {
			throw new BadRequestException();
		}
		if (getUser().getPlans().stream()
				.anyMatch(plan -> plan.getName().equals(planInput.getPlan().getName()))) {
			throw new UnprocessableEntityException();
		}
		return Utils.withPlanDao(dao -> {
			Plan plan = dao.getPlanById(planId);
			if (plan == null || !getUser().equals(plan.getUser())) {
				throw new NotFoundException();
			}
			plan.setName(planInput.getPlan().getName());
			dao.updatePlan(plan);
			planInput.getPlan().setIdentifier(planId);
			return planInput;
		});
	}

	/**
	 * DELETE plans/{planId} request handler.
	 *
	 * Deletes the plan with the given id.
	 * 
	 * @param planId
	 *            the plan's id
	 * @return OK 200 if successful.
	 */
	@DELETE
	@Path("/{id}")
	@AuthorizationNeeded
	public Response deletePlan(@PathParam("id") String planId) {
		return Utils.withPlanDao(dao -> {
			Plan plan = dao.getPlanById(planId);
			if (plan == null || !getUser().equals(plan.getUser())) {
				throw new NotFoundException();
			}
			dao.deletePlan(plan);
			return Response.ok().build();
		});
	}

	/* ******************************  /{id}/modules  ******************************** */

	/**
	 * GET plans/{planId}/modules request handler. Returns the modules which
	 * meet the given filters' conditions. The modules' preferences are also
	 * returned which requires the plan to be known.
	 * 
	 * @param planId
	 *            the plan's id
	 * @param uriInfo
	 *            UriInfo object providing access to the GET parameters
	 *            containing the filter settings.
	 * @return the JSON representation of the filtered modules.
	 */
	@GET
	@Path("/{id}/modules")
	@Produces(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public Map<String, List<JsonModule>> getModules(@PathParam("id") String planId, @Context UriInfo uriInfo) {
		User user = getUser();
		if (user.getDiscipline() == null) {
			throw new UnprocessableEntityException();
		}
		return Utils.withPlanDao(planDao -> {
			Plan plan = planDao.getPlanById(planId);
			if (plan == null || !getUser().equals(plan.getUser())) {
				throw new NotFoundException();
			}
			Filter filter = ModuleResource.getFilterFromRequest(uriInfo.getQueryParameters(), user.getDiscipline());
			if (filter == null) {
				throw new BadRequestException();
			}
			return Utils.withModuleDao(moduleDao -> {
				List<JsonModule> result = moduleDao.getModulesByFilter(filter, user.getDiscipline()).stream().map(m -> {
					JsonModule newModule = new JsonModule();
					newModule.setId(m.getIdentifier());
					newModule.setName(m.getName());
					newModule.setCreditPoints(m.getCreditPoints());
					newModule.setLecturer(m.getModuleDescription().getLecturer());
					newModule.setCycleType(m.getCycleType());
					newModule.setPreference(plan.getPreferenceForModule(m));
					return newModule;
				}).collect(Collectors.toList());
				return SimpleJsonResponse.build("modules", result);
			});
		});
	}

	/**
	 * GET /plans/{planId}/modules/{moduleId} request handler.
	 *
	 * @param planId
	 *            id of the current plan
	 * @param moduleId
	 *            id of the requested module
	 * @return the requested module's JSON representation. (PlanId is needed for
	 *         determining the preference status of the module.)
	 */
	@GET
	@Path("/{plan}/modules/{module}")
	@Produces(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public Map<String, ModuleDto> getModule(@PathParam("plan") String planId, @PathParam("module") String moduleId) {
		return Utils.withPlanDao(planDao -> Utils.withModuleDao(moduleDao -> {
			Plan plan = planDao.getPlanById(planId);
			if (plan == null || !getUser().equals(plan.getUser())) {
				throw new NotFoundException();
			}
			Module module = moduleDao.getModuleById(moduleId);
			if (module == null) {
				throw new NotFoundException();
			}
			ModuleDto result = new ModuleDto(module, plan);
			return SimpleJsonResponse.build("module", result);
		}));
	}

	/**
	 * PUT /plans/{plan}/modules/{module} request handler. Inserts module into
	 * given plan at given position (= semester row).
	 *
	 * @param planId
	 *            the plan id
	 * @param moduleId
	 *            the id of the module to insert
	 * @param moduleInput
	 *            the JsonModule object holding the semester number
	 * @return moduleInput
	 */
	@PUT
	@Path("/{plan}/modules/{module}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public ModuleInOut putModuleSemester(@PathParam("plan") String planId, @PathParam("module") String moduleId,
			ModuleInOut moduleInput) {
		if (moduleInput == null 
				|| moduleInput.getModule() == null 
				|| !Objects.equals(moduleInput.getModule().getId(), moduleId)
				|| moduleInput.getModule().getSemester() == null) {
			throw new BadRequestException();
		}
		return Utils.withPlanDao(planDao -> Utils.withModuleDao(moduleDao -> {
			Plan plan = planDao.getPlanById(planId);
			if (plan == null || !getUser().equals(plan.getUser())) {
				throw new NotFoundException();
			}
			Module module = moduleDao.getModuleById(moduleId);
			if (module == null) {
				throw new NotFoundException();
			}
			if (plan.getEntryFor(module) != null) {
				plan.getEntryFor(module).setSemester(moduleInput.getModule().getSemester());
			} else {
				plan.getModuleEntries().add(new ModuleEntry(module, moduleInput.getModule().getSemester()));
			}
			plan.setVerificationState(VerificationState.NOT_VERIFIED);
			planDao.updatePlan(plan);
			return moduleInput;
		}));
	}

	/**
	 * DELETE /plans/{plan}/modules/{module} request handler.
	 *
	 * Deletes module from given plan.
	 *
	 * @param planId
	 *            the plan id
	 * @param moduleId
	 *            the id of the module ro remove.
	 * @return OK 200 if successful.
	 */
	@DELETE
	@Path("/{plan}/modules/{module}")
	@AuthorizationNeeded
	public Response removeModuleSemester(@PathParam("plan") String planId, @PathParam("module") String moduleId) {
		return Utils.withPlanDao(planDao -> Utils.withModuleDao(moduleDao -> {
			Plan plan = planDao.getPlanById(planId);
			if (plan == null || !getUser().equals(plan.getUser())) {
				throw new NotFoundException();
			}
			Module module = moduleDao.getModuleById(moduleId);
			if (module == null) {
				throw new NotFoundException();
			}
			ModuleEntry moduleEntry = plan.getModuleEntries().stream()
					.filter(entry -> entry.getModule().equals(module))
					.findFirst().orElseThrow(UnprocessableEntityException::new);
			plan.getModuleEntries().remove(moduleEntry);
			plan.setVerificationState(VerificationState.NOT_VERIFIED);
			planDao.updatePlan(plan);
			return Response.ok().build();
		}));
	}

	/**
	 * PUT /plans/{plan}/modules/{module}/preference request handler. Sets a
	 * preference for the given module in context of the given plan.
	 *
	 * @param planId
	 *            the plan id
	 * @param moduleId
	 *            the id of the module to set the preference for
	 * @param moduleInput
	 *            the JsonModule object holding the preference to assign
	 * @return moduleInput
	 */
	@PUT
	@Path("/{plan}/modules/{module}/preference")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public ModuleInOut setModulePreference(@PathParam("plan") String planId, @PathParam("module") String moduleId,
			ModuleInOut moduleInput) {
		if (moduleInput == null
				|| moduleInput.getModule() == null 
				|| !Objects.equals(moduleInput.getModule().getId(), moduleId)) {
			throw new BadRequestException();
		}
		return Utils.withModuleDao(moduleDao -> Utils.withPlanDao(planDao -> {
			Module module = moduleDao.getModuleById(moduleId);
			if (module == null) {
				throw new NotFoundException();
			}
			Plan plan = planDao.getPlanById(planId);
			if (plan == null || !getUser().equals(plan.getUser())) {
				throw new NotFoundException();
			}
			List<ModulePreference> preferences = plan.getPreferences();
			if (moduleInput.getModule().getPreference() == null) {
				if (preferences.stream().noneMatch(preference -> preference.getModule().equals(module))) {
					throw new UnprocessableEntityException();
				}
				preferences.removeIf(preference -> preference.getModule().equals(module));
			} else {
				Optional<ModulePreference> foundPreference = preferences.stream()
						.filter(preference -> preference.getModule().equals(module))
						.findFirst();
				if (foundPreference.isPresent()) {
					foundPreference.get().setPreference(moduleInput.getModule().getPreference());
				} else {
					preferences.add(new ModulePreference(module, moduleInput.getModule().getPreference(), plan));
				}
			}
			planDao.updatePlan(plan);
			return moduleInput;
		}));

	}

	/* ******************************  /{plan}/verification  ******************************** */

	/**
	 * GET plans/{plan}/verification request handler. Verifies the plan with the
	 * given id, saves its new verification state into the database and returns
	 * it along with violations, field-violations and group-violations, if the
	 * plan is incorrect.
	 *
	 * @param planId
	 *            the planId
	 * @return the JSON response with status and described violations.
	 */
	@GET
	@Path("/{plan}/verification")
	@Produces(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public Map<String, VerificationResult> verifyPlan(@PathParam("plan") String planId) {
		return Utils.withPlanDao(dao -> {
			Plan plan = dao.getPlanById(planId);
			if (plan == null || !getUser().equals(plan.getUser())) {
				throw new NotFoundException();
			}
			VerificationManager manager = new VerificationManager();
			VerificationResult result = manager.verify(plan);
			plan.setVerificationState(result.isCorrect() ? VerificationState.VALID : VerificationState.INVALID);
			dao.updatePlan(plan);
			return SimpleJsonResponse.build("plan", result);
		});
	}

	/* ******************************  /{plan}/proposal/{objectiveId}  ******************************** */


	/**
	 * GET /plans/{plan}/proposal/{objectiveId} Generates a proposal for the
	 * given plan by following given GET parameters and maximizing the given
	 * objective function.
	 *
	 * @param planId
	 *            the id of the initial plan
	 * @param objectiveId
	 *            the id of the objective function to use
	 * @param maxSemesterEcts
	 *            maximum number of credits per semester, as specified by user
	 * @param uriInfo
	 * 			  contains given GET parameters
	 * @return the generated plan's JSON representation.
	 */
	@GET
	@Path("/{plan}/proposal/{objectiveId}")
	@Produces(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public PlanInOut generatePlan(@PathParam("plan") String planId, 
			@PathParam("objectiveId") int objectiveId,
			@QueryParam("max-semester-ects") @NotNull Integer maxSemesterEcts, 
			@Context UriInfo uriInfo) {
		return Utils.withPlanDao(planDao -> Utils.withModuleDao(moduleDao -> {
			Plan plan = planDao.getPlanById(planId);
			if (plan == null || !getUser().equals(plan.getUser())) {
				throw new NotFoundException();
			}

			MultivaluedMap<String, String> parameters = uriInfo.getQueryParameters();

			if (maxSemesterEcts == null) {
				throw new BadRequestException();
			}
			try {
				Map<Field, Category> preferredSubjects = getPreferredSubjectsFromRequest(parameters);

				GenerationManager manager = new GenerationManager();
				PartialObjectiveFunction function = manager.getAllObjectiveFunctions().get(objectiveId);
				if (function == null) {
					throw new NotFoundException();
				}
				Plan result = manager.generate(function, plan, moduleDao, preferredSubjects, maxSemesterEcts);
				return new PlanInOut(result);
				// TODO Check serialization of `result` inside generator
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
				throw new BadRequestException();
			}
		}));
	}

	private Map<Field, Category> getPreferredSubjectsFromRequest(MultivaluedMap<String, String> parameters) {
		if (!parameters.containsKey("fields")) {
			throw new BadRequestException();
		}
		Map<Field, Category> result = new HashMap<>();
		try {
			Utils.useModuleDao(moduleDao -> {
				Arrays.stream(parameters.getFirst("fields").split(",")).forEach(fieldIdStr -> {
					Field key = moduleDao.getFieldById(Integer.parseInt(fieldIdStr));
					Category value = moduleDao
							.getCategoryById(Integer.parseInt(parameters.getFirst("field-" + fieldIdStr)));
					result.put(key, value);
				});
			});
		} catch (IllegalArgumentException ex) {
			throw new BadRequestException();
		}
		return result;
	}

	/**
	 * GET /plans/{planId}/pdf request handler.
	 *
	 *
	 *
	 * @param planId
	 *            the plan to export to PDF.
	 * @param accessToken
	 *            access token for client authentification.
	 * @return a PDF / HTML rendered version of the plan.
	 */
	@GET
	@Path("/{planId}/pdf")
	@Produces("text/html")
	public Response convertPlanToPDF(@PathParam(value = "planId") String planId,
			@QueryParam("access-token") String accessToken) {
		AbstractSecurityProvider provider = AbstractSecurityProvider.getSecurityProviderImpl();
		AuthorizationContext context = provider.getAuthorizationContext(accessToken);
		Plan plan = PlanDaoFactory.getPlanDao().getPlanById(planId);
		if (plan == null) {
			throw new NotFoundException();
		}
		if (context == null || !context.getUser().equals(plan.getUser())) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		PlanConverter converter;
		try {
			converter = new PlanConverter(plan);
		} catch (IOException e) {
			throw new ServiceUnavailableException();
		}
		converter.getWriter().flush();
		return Response.ok(converter.getWriter().toString()).build();
	}

	private boolean isValid(ModuleEntry entry, Semester studyStart) {
		if (entry.getModule() == null) {
			return false;
		}
		if (entry.getSemester() <= 0 || entry.getSemester() > MAX_SEMESTERS) {
			return false;
		}
		//TODO: the drop
		return true;
	}


	/**
	 * Class for encapsulating a single JsonModule. Used for JSON
	 * de-/serialization.
	 */
	static class ModuleInOut {
		@JsonProperty("module")
		@NotNull
		private JsonModule module;

		/**
		 * Creates an empty ModuleInOut.
		 */
		ModuleInOut() {
		}

		/**
		 * Creates a ModuleInOut with a given JsonModule.
		 * 
		 * @param module
		 *            the JsonModule instance
		 */
		ModuleInOut(JsonModule module) {
			this.module = module;
		}

		/**
		 *
		 * @return the JsonModule
		 */
		public JsonModule getModule() {
			return module;
		}

		/**
		 * Sets the JsonModule
		 * 
		 * @param module
		 *            the JsonModule
		 */
		public void setModule(JsonModule module) {
			this.module = module;
		}
	}

	/**
	 * Class for encapsulating a single Plan. Used for JSON de-/serialization.
	 */
	static class PlanInOut {
		@JsonProperty("plan")
		@NotNull
		private Plan plan;

		/**
		 * Empty constructor.
		 */
		PlanInOut() {
		}

		/**
		 * Constructor with a plan parameter.
		 * 
		 * @param plan
		 *            the plan
		 */
		PlanInOut(Plan plan) {
			this.plan = plan;
			plan.getPreferences();
			plan.getCreditPoints();
		}

		/**
		 *
		 * @return the plan
		 */
		public Plan getPlan() {
			return plan;
		}

		/**
		 * Sets the plan
		 * 
		 * @param plan
		 *            the plan
		 */
		public void setPlan(Plan plan) {
			this.plan = plan;
		}
	}
	
	/**
	 * Annotation for denoting PATCH requests.
	 */
	@Target({ ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	@HttpMethod("PATCH")
	@interface PATCH {
	}
};
