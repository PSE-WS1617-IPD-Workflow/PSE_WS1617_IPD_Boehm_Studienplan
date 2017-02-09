package edu.kit.informatik.studyplan.server.rest.resources;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.filter.*;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.rest.GetParameters;
import edu.kit.informatik.studyplan.server.rest.JSONObject;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;
import edu.kit.informatik.studyplan.server.rest.resources.json.JsonModule;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Diese Klasse repräsentiert die Planmodule-Ressource.
 */
public class PlanModulesResource {









	/**
	 * PUT-Anfrage: setzt eine Bewertung als JSON Objekt für den Modul mit der
	 * gegebenen ID, der im Plan mit der gegebenen ID.
	 * 
	 * @param planID
	 *            ID des zu bearbeitenden Plans.
	 * @param moduleID
	 *            ID des zu bewertenden Modul.
	 * @param jsonModulePreference
	 *            die zu setzenden Bewertung des Moduls als GetParameters.
	 * @return die gesetzten Bewertung des Moduls als JSON Objekt.
	 */
	public JSONObject setModulePreference(String planID, String moduleID, GetParameters jsonModulePreference) {
		return null;
	}

	/**
	 * GET-Anfrage: Gibt den angefragten Filter zurück.
	 * 
	 * @param params
	 *            Anfrage als eine mehrwertige Zuordnung von Strings.
	 * @return den angefragten Filter.
	 */
	public static Filter getFilterFromRequest(@NotNull MultivaluedMap<String, String> params, Discipline discipline) {
		if (!params.containsKey("filters")) {
			return new TrueFilter();
		} else {
			List<String> filterNames = params.get("filters");
			if (Utils.hasDuplicates(filterNames)) {
				throw new BadRequestException();
			}
			try {
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
									filter = new CategoryFilter(category, discipline);
									break;
								case "type":
									int type = Integer.parseInt(params.getFirst("type"));
									filter = new ModuleTypeFilter(type);
									break;
								case "compulsory":
									int compulsory = Integer.parseInt(params.getFirst("compulsory"));
									filter = new CompulsoryFilter(compulsory);
									break;
								case "cycletype":
									int cycletype = Integer.parseInt(params.getFirst("cycletype"));
									filter = new CycleTypeFilter(cycletype);
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
			} catch (IllegalArgumentException ex) {
				throw new BadRequestException();
			}
		}
	}

}
