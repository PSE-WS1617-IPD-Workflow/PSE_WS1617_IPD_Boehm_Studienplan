package edu.kit.informatik.studyplan.server.rest.resources;

import edu.kit.informatik.studyplan.server.filter.FilterDescriptor;
import edu.kit.informatik.studyplan.server.filter.FilterDescriptorProvider;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.userdata.dao.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Diese Klasse repräsentiert die Filter-Ressource.
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
	 * GET Anfrage: Gibt eine Liste aller vorhandenen Filtern zurück.
	 * 
	 * @return eine Liste von Filtern.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> getAllFilters() {
		Discipline discipline = getUser().getDiscipline();
		if (discipline == null) {
			throw new UnprocessableEntityException();
		}
		return new FilterDescriptorProvider(discipline).values().stream()
				.map(FilterDescriptor::toJson)
				.collect(Collectors.toList());
	}
};
