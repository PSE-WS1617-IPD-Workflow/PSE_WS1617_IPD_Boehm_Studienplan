package edu.kit.informatik.studyplan.server.rest.resources;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.filter.*;
import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
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
 * Diese Klasse repräsentiert die Modul-Ressource.
 */
@Path("/modules")
public class ModuleResource {
	@Inject
	Provider<AuthorizationContext> context;

	private User getUser() {
		return context.get().getUser();
	}

	/**
	 * GET Anfrage: Gibt eine Liste der JSON-Representationen von Modulen, die
	 * dem gegebenen Filter entsprechen, zurück.
	 *
	 * @return eine Liste der JSON-Representationen von Modulen.
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
	 * GET-Anfrage: Gibt den angefragten Filter zurück.
	 *
	 * @param params
	 *            Anfrage als eine mehrwertige Zuordnung von Strings.
	 * @return den angefragten Filter.
	 */
	public static Filter getFilterFromRequest(MultivaluedMap<String, String> params, Discipline discipline) {
		if (!params.containsKey("filters")) {
			return new TrueFilter();
		} else {
			List<String> filterNames = params.get("filters");
			if (Utils.hasDuplicates(filterNames)) {
				throw new BadRequestException();
			}
			try {
				return Utils.withModuleDao(dao -> {
					FilterDescriptorProvider descriptors = new FilterDescriptorProvider(discipline);
					List<Filter> filters = filterNames.stream()
							.map(filterName -> {
								Filter filter = null;
								switch (filterName) {
									case "ects":
										int ectsMin = Integer.parseInt(params.getFirst("ects-min"));
										int ectsMax = Integer.parseInt(params.getFirst("ects-max"));
										filter = new CreditPointsFilter(ectsMin, ectsMax, 0, 30);
										break;
									case "category":
										int category = Integer.parseInt(params.getFirst("category"));
										filter = new CategoryFilter(dao.getCategoryById(category), discipline);
										break;
									case "type":
										int type = Integer.parseInt(params.getFirst("type"));
										filter = new ModuleTypeFilter(dao.getModuleTypes().get(type));
										break;
									case "compulsory":
										int compulsory = Integer.parseInt(params.getFirst("compulsory"));
										filter = new CompulsoryFilter(compulsory == 0);
										break;
									case "cycletype":
										int cycletype = Integer.parseInt(params.getFirst("cycletype"));
										CycleType defaultCycleType = ((CycleTypeFilter) descriptors.CYCLE_TYPE()
												.getDefaultFilter()).getItemObjects().get(0);
										filter = new CycleTypeFilter(defaultCycleType);
										break;
									case "name":
										String name = params.getFirst("name");
										filter = new NameFilter(name);
										break;
									default:
										throw new BadRequestException();
								}
								return filter;
							})
							.collect(Collectors.toList());
					return new MultiFilter(filters);
				});
			} catch (IllegalArgumentException | NullPointerException ex) {
				throw new BadRequestException();
			}
		}
	}
}
