package edu.kit.informatik.studyplan.server.rest.resources;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.filter.FilterDescriptorProvider;
import edu.kit.informatik.studyplan.server.filter.MultiFilter;
import edu.kit.informatik.studyplan.server.filter.TrueFilter;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.dao.AuthorizationContext;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;
import edu.kit.informatik.studyplan.server.rest.resources.json.JsonModule;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST resource for /modules.
 */
@Path("/modules")
public class ModuleResource {
	@Inject
	Provider<AuthorizationContext> context;

	private User getUser() {
		return context.get().getUser();
	}

	/**
	 * GET request handler.
	 * Returns the modules which meet the given filters' conditions.
	 * @param uriInfo
	 *            UriInfo object providing access to the GET parameters containing the filter settings.
	 * @return the JSON representation of the filtered modules.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@AuthorizationNeeded
	public Map<String, List<JsonModule>> getModules(@Context UriInfo uriInfo) {
		User user = getUser();
		if (user.getDiscipline() == null) {
			throw new UnprocessableEntityException();
		}
		Filter filter = getFilterFromRequest(uriInfo.getQueryParameters(),
				user.getDiscipline());
		if (filter == null) {
			throw new BadRequestException();
		}
		List<JsonModule> result = Utils.withModuleDao(dao -> dao
                .getModulesByFilter(filter, user.getDiscipline())
                .stream().map(m -> {
                    JsonModule newModule = new JsonModule();
                    newModule.setId(m.getIdentifier());
                    newModule.setName(m.getName());
                    newModule.setCreditPoints(m.getCreditPoints());
                    newModule.setLecturer(m.getModuleDescription().getLecturer());
                    newModule.setCycleType(m.getCycleType());
                    return newModule;
                })
                .collect(Collectors.toList()));
		return SimpleJsonResponse.build("modules", result);
	}

	/**
	 * GET /modules/{moduleId} request handler.
	 *
	 * @param moduleId
	 *            id of the requested module
	 * @return the requested module's JSON representation.
	 */
	@GET
	@Path("/{moduleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, JsonModule> getModule(@PathParam("moduleId") String moduleId) {
		return Utils.withModuleDao(dao -> {
			Module module = dao.getModuleById(moduleId);
			if (module == null) {
				throw new NotFoundException();
			}
			JsonModule result = JsonModule.fromModule(module, null, null);
			return SimpleJsonResponse.build("module", result);
		});
	}

	/**
	 * Extracts a filter out of a given GET parameter map containing the specified filter settings.
	 *
	 * @param params the GET parameters to extract the filter from
	 * @param discipline the discipline of the user (needed for category filtering).
	 * @return the extracted filter.
	 */
	static Filter getFilterFromRequest(MultivaluedMap<String, String> params, Discipline discipline) {
		if (!params.containsKey("filters")) {
			return new TrueFilter();
		} else {
			List<String> filterNames = params.get("filters");
			if (Utils.hasDuplicates(filterNames)) {
				throw new BadRequestException();
			}
			return Utils.withModuleDao(dao -> {
				FilterDescriptorProvider descriptors = new FilterDescriptorProvider(discipline);
				List<Filter> filters = filterNames.stream()
						.map(filterName ->
							descriptors.getDescriptorFromUriIdentifier(filterName).getFilterFromRequest(params))
						.collect(Collectors.toList());
				return new MultiFilter(filters);
			});
		}
	}
}
