package edu.kit.informatik.studyplan.server.rest.resources;

import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDao;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDaoFactory;
import edu.kit.informatik.studyplan.server.rest.resources.json.JsonModule;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Diese Klasse repräsentiert die Modul-Ressource.
 */
@Path("/modules")
public class ModuleResource {
	@Inject
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
		User user = context.getUser();
		if (user.getDiscipline() == null)
			throw new UnprocessableEntityException();
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
					newModule.setCycleType(m.getCycleType());
					return newModule;
				})
				.collect(Collectors.toList());
		return SimpleJsonResponse.build("modules", result);
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
		Module module = getModuleDao().getModuleById(moduleId);
		if (module == null)
			throw new NotFoundException();
		JsonModule result = JsonModule.fromModule(module, null, null);
		return SimpleJsonResponse.build("module", result);
	}

	private ModuleDao getModuleDao() {
		return ModuleDaoFactory.getModuleDao();
	}

	private UserDao getUserDao() {
		return UserDaoFactory.getUserDao();
	}
}
