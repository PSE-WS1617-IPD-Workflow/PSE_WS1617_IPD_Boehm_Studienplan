package edu.kit.informatik.studyplan.server.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.dao.AbstractSecurityProvider;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDao;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDaoFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Diese Klasse repräsentiert die Modul-Ressource.
 */
@Path("/modules")
public class ModuleResource {
	@Context
	AuthorizationContext context;

	/**
	 * GET Anfrage: Gibt eine Liste der JSON-Representationen von Modulen, die
	 * dem gegebenen Filter entsprechen, zurück.
	 *
	 * @return eine Liste der JSON-Representationen von Modulen.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List<JsonModule>> getModules(@Context UriInfo uriInfo) {
		try {
			User user = context.getUser();
			Filter filter = PlanModulesResource.getFilterFromRequest(uriInfo.getQueryParameters());
			if (filter == null)
				throw new BadRequestException();
			List<JsonModule> result = getModuleDao()
					.getModulesByFilter(filter, user.getDiscipline())
					.parallelStream().map(m -> {
						JsonModule newModule = new JsonModule();
						newModule.setId(m.getIdentifier());
						newModule.setName(m.getName());
						newModule.setCreditPoints(m.getCreditPoints());
						newModule.setLecturer(m.getModuleDescription().getLecturer());
						return newModule;
					})
					.collect(Collectors.toList());
			return SimpleJsonResponse.build("modules", result);
		} catch(NullPointerException ex) {
			throw new InternalServerErrorException();
		}
	}

	/**
	 * Get Anfrage: Gibt eine JSON-Representation von dem Modul mit der
	 * gegebenen ID zurück.
	 * 
	 * @param moduleId
	 *            ID des erforderten Modul.
	 * @return eine JSON-Representation von dem Modul.
	 */
	@GET
	@Path("/{moduleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, JsonModule> getModule(@PathParam("moduleId") String moduleId) {
		try {
			Module module = getModuleDao().getModuleById(moduleId);
			if (module == null)
				throw new BadRequestException();
			JsonModule result = JsonModule.fromModule(module, null, null);
			return SimpleJsonResponse.build("module", result);
		} catch(NullPointerException ex) {
			throw new InternalServerErrorException();
		}
	}

	static class JsonModule {
		private String id;
		private String name;
		private List<Category> categories;
		private Integer semester;
		private CycleType cycleType;
		private Double creditPoints;
		private String lecturer;
		@JsonInclude(JsonInclude.Include.ALWAYS)
		@JsonSerialize(using = MyObjectMapperProvider.CustomSerializerModule.PreferenceTypeSerializer.class)
		@JsonDeserialize(using = MyObjectMapperProvider.CustomSerializerModule.PreferenceTypeDeserializer.class)
		private PreferenceType preference;
		private Boolean compulsory;
		private String description;
		private List<ModuleConstraint> constraints;

		public JsonModule() {} //for Jackson

		public JsonModule(String id,
						  String name,
						  List<Category> categories,
						  Integer semester,
						  CycleType cycleType,
						  Double creditPoints,
						  String lecturer,
						  PreferenceType preference,
						  Boolean compulsory,
						  String description,
						  List<ModuleConstraint> constraints) {
			this.setId(id);
			this.setName(name);
			this.setCategories(categories);
			this.setSemester(semester);
			this.setCycleType(cycleType);
			this.setCreditPoints(creditPoints);
			this.setLecturer(lecturer);
			this.setPreference(preference);
			this.setCompulsory(compulsory);
			this.setDescription(description);
			this.setConstraints(constraints);
		}

		public static JsonModule fromModule(Module module, Integer semester, PreferenceType preference) {
			return new JsonModule(module.getIdentifier(), module.getName(), module.getCategories(), semester,
					module.getCycleType(), module.getCreditPoints(), module.getModuleDescription().getLecturer(),
					preference, module.isCompulsory(), module.getModuleDescription().getDescriptionText(),
					module.getConstraints());
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Category> getCategories() {
			return categories;
		}

		public void setCategories(List<Category> categories) {
			this.categories = categories;
		}

		public Integer getSemester() {
			return semester;
		}

		public void setSemester(Integer semester) {
			this.semester = semester;
		}

		public CycleType getCycleType() {
			return cycleType;
		}

		public void setCycleType(CycleType cycleType) {
			this.cycleType = cycleType;
		}

		public Double getCreditPoints() {
			return creditPoints;
		}

		public void setCreditPoints(Double creditPoints) {
			this.creditPoints = creditPoints;
		}

		public String getLecturer() {
			return lecturer;
		}

		public void setLecturer(String lecturer) {
			this.lecturer = lecturer;
		}

		public PreferenceType getPreference() {
			return preference;
		}

		public void setPreference(PreferenceType preference) {
			this.preference = preference;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public List<ModuleConstraint> getConstraints() {
			return constraints;
		}

		public void setConstraints(List<ModuleConstraint> constraints) {
			this.constraints = constraints;
		}

		public Boolean isCompulsory() {
			return compulsory;
		}

		public void setCompulsory(Boolean compulsory) {
			this.compulsory = compulsory;
		}
	}

	private ModuleDao getModuleDao() {
		return ModuleDaoFactory.getModuleDao();
	}

	private UserDao getUserDao() {
		return UserDaoFactory.getUserDao();
	}
}
