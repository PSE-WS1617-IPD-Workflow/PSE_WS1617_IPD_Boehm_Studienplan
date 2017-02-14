package edu.kit.informatik.studyplan.server.rest.resources;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.kit.informatik.studyplan.server.filter.FilterDescriptor;
import edu.kit.informatik.studyplan.server.filter.FilterDescriptorProvider;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

/**
 * REST resource for /filters.
 */
@Path("/filters")
@AuthorizationNeeded
public class FilterResource {
	@Inject
	Provider<AuthorizationContext> context;

	private User getUser() {
		return context.get().getUser();
	}

	/**
	 * GET request handler.
	 * 
	 * @return a list of all available filters as JSON.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List<Map<String, Object>>> getAllFilters() {
		Discipline discipline = getUser().getDiscipline();
		if (discipline == null) {
			throw new UnprocessableEntityException();
		}
		List<Map<String, Object>> result = new FilterDescriptorProvider(discipline).values().stream()
				.map(FilterDescriptor::toJson)
				.collect(Collectors.toList());
		return SimpleJsonResponse.build("filters", result);
	}
};
